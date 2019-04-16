package th.co.knightfrank.kf_care_android.customer.screens.profile.editprofile

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.users.GetUserByUserIDRequest
import th.co.knightfrank.data_java.data.requests.users.UpdateCustomerInfoRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.ImageUtilities
import th.co.knightfrank.kf_care_android.customer.components.IntentBuilder
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import java.io.File
import javax.inject.Inject

class ProfileEditActivity : BaseActivity() {
    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        private const val REQ_TAKE_PHOTO_1 = 101
        private const val REQ_IMAGE_1 = 201

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, ProfileEditActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private var mCurrentPhotoPath: String = ""
    private var base64StringImage1: String? = null

    private var isLineContact: Boolean? = false
    private var isMobileContact: Boolean? = false

    override fun getLayoutId(): Int = R.layout.activity_profile_edit

    @Inject
    lateinit var appSettings: AppSettings

    private lateinit var viewModel: ProfileEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileEditViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.getUserByUserIDRequest(
                    GetUserByUserIDRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID)
                    )
            )
        }

        val items = arrayOf(getString(R.string.get_picture), getString(R.string.take_photo))
        relative_layout_selected_image1.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.attach))
                    .setItems(items, { dialog, which ->
                        when (items[which]) {
                            getString(R.string.get_picture) -> {
                                val intent = IntentBuilder.imageSelector("Select Image")
                                this.startActivityForResult(intent, REQ_IMAGE_1)
                            }
                            getString(R.string.take_photo) -> {
                                launchCamera(REQ_TAKE_PHOTO_1)
                            }
                        }
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }

        image_view_delete_image1.setOnClickListener {
            clearSelectedImage(1)
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is ProfileEditViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ProfileEditViewModel.Status.Success -> {
                    edit_text_first_name.setText(
                            it.viewDataBundle.userInfoParcel?._firstName
                    )

                    edit_text_last_name.setText(
                            it.viewDataBundle.userInfoParcel?._lastName
                    )

                    edit_text_tel_no.setText(
                            it.viewDataBundle.userInfoParcel?._mobileNo
                    )

                    edit_text_line_id.setText(
                            it.viewDataBundle.userInfoParcel?._lineID
                    )

                    edit_text_email.setText(
                            it.viewDataBundle.userInfoParcel?._email
                    )

                    edit_text_car_parking.setText(
                            it.viewDataBundle.userInfoParcel?._parkingNo
                    )

                    edit_text_car_plate.setText(
                            it.viewDataBundle.userInfoParcel?._carRegistration
                    )

                    //init load set theme for is type
                    isLineContact = it.viewDataBundle.userInfoParcel?._isContactLine
                    isMobileContact = it.viewDataBundle.userInfoParcel?._isContactMobile

                    isLineContactButtonState(isLineContact)
                    isMobileContactButtonState(isMobileContact)

                    btn_is_line.setOnClickListener {
                        isLineContact = !isLineContact!!
                        isLineContactButtonState(isLineContact)
                    }

                    btn_is_mobile.setOnClickListener {
                        isMobileContact = !isMobileContact!!
                        isMobileContactButtonState(isMobileContact)
                    }

                    if (!it.viewDataBundle.userInfoParcel?._imagePath.isNullOrEmpty()) {
                        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(it.viewDataBundle.userInfoParcel?._imagePath))
                                .setResizeOptions(ResizeOptions(1280, 720))
                                .build()
                        val controller = Fresco.newDraweeControllerBuilder()
                                .setOldController(image_view_selected_image1.controller)
                                .setImageRequest(request)
                                .build()
                        image_view_selected_image1.controller = controller
                        image_view_delete_image1.visibility = View.VISIBLE
                    } else {
                        image_view_delete_image1.visibility = View.GONE
                    }

                    var fileName: String? = null

                    btn_edit_profile.setOnClickListener {
                        if (!base64StringImage1.isNullOrEmpty()) {
                            fileName = "user_profile_image1.jpg"
                        }

                        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                            viewModel.updateCustomerInfoRequest(
                                    UpdateCustomerInfoRequest(
                                            _email = edit_text_email.text.toString(),
                                            _userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                            _token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                            _projectID = viewModel.currentValue.viewDataBundle.userInfoParcel?._projectInfo?._projectID,
                                            _roomID = viewModel.currentValue.viewDataBundle.userInfoParcel?._roomInfo?._roomID,
                                            _buildingID = viewModel.currentValue.viewDataBundle.userInfoParcel?._buildingInfo?._buildingID,
                                            _carRegistration = edit_text_car_plate.text.toString(),
                                            _firebaseToken = appSettings.getValue(AppSettingsKey.FIREBASE_TOKEN),
                                            _firstName = edit_text_first_name.text.toString(),
                                            _floorID = viewModel.currentValue.viewDataBundle.userInfoParcel?._floorInfo?._floorID,
                                            _imageContent = base64StringImage1,
                                            _imageFileName = fileName,
                                            _isContactLine = isLineContact,
                                            _isContactMobile = isMobileContact,
                                            _lastName = edit_text_last_name.text.toString(),
                                            _lineID = edit_text_line_id.text.toString(),
                                            _mobileNo = edit_text_tel_no.text.toString(),
                                            _parkingNo = edit_text_car_parking.text.toString(),
                                            _oldPassword = if (edit_text_old_password.text.toString().isEmpty()) {
                                                null
                                            } else {
                                                edit_text_old_password.text.toString()
                                            },
                                            _newPassword = if (edit_text_new_password.text.toString().isEmpty()) {
                                                null
                                            } else {
                                                edit_text_new_password.text.toString()
                                            },
                                            _confirmPassword = if (edit_text_confirm_password.text.toString().isEmpty()) {
                                                null
                                            } else {
                                                edit_text_confirm_password.text.toString()
                                            },
                                            _registerType = viewModel.currentValue.viewDataBundle.userInfoParcel?._registerType
                                    )
                            )
                        }
                    }
                }

                is ProfileEditViewModel.Status.SubmitSuccess -> {
                    alertFacade.success(it.viewDataBundle.headerResponse!!._responseDesc!!, this)

                    AlertDialog.Builder(this)
                            .setMessage(resources.getString(R.string.profile_edit_dialog_content))
                            .setPositiveButton(resources.getString(R.string.profile_edit_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                                this.finish()
                            })
                            .create()
                            .show()
                }

                is ProfileEditViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is ProfileEditViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, "Invalid token, Please Login Again!!!")
                    }
                }

                is ProfileEditViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("CreateInfoFragment", "requestCode : " + requestCode)
        Log.e("CreateInfoFragment", "resultCode : " + resultCode)
        Log.e("CreateInfoFragment", "data : " + data)
        when (requestCode) {
            REQ_IMAGE_1 -> {
                if (resultCode == RESULT_OK && data != null) {
                    processGetImageFromGallery(data.data.toString(), requestCode)
                }
            }
            REQ_TAKE_PHOTO_1 -> {
                if (resultCode == RESULT_OK) {
                    processCapturedPhoto(requestCode)
                }
            }
        }
    }

    private fun launchCamera(requestFromLayout: Int) {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = this.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(this.packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            this.startActivityForResult(intent, requestFromLayout)
        }
    }

    private fun processCapturedPhoto(requestFromLayout: Int) {
        val cursor = this.contentResolver.query(Uri.parse(mCurrentPhotoPath), Array(1) {
            MediaStore.Images.ImageColumns.DATA
        }, null, null, null)
        cursor.moveToFirst()
        val photoPath = cursor.getString(0)
        cursor.close()

        val file = File(photoPath)
        val uri = Uri.fromFile(file)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(1280, 720))
                .build()

        when (requestFromLayout) {
            REQ_TAKE_PHOTO_1 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image1.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image1.controller = controller
                base64StringImage1 = ImageUtilities.encoderJPEGByteArrayToBase64Camera(this.contentResolver, uri, 1280, 720, 100)

                image_view_delete_image1.visibility = View.VISIBLE
            }
            else -> {
                alertFacade.error("Something went wrong.", this)
            }
        }
    }

    private fun processGetImageFromGallery(photoPath: String, requestFromLayout: Int) {
        val uri = Uri.parse(photoPath)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(1280, 720))
                .build()

        when (requestFromLayout) {
            REQ_IMAGE_1 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image1.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image1.controller = controller
                base64StringImage1 = ImageUtilities.encoderJPEGByteArrayToBase64Gallery(this.contentResolver, uri, 1280, 720, 100)

                image_view_delete_image1.visibility = View.VISIBLE
            }
            else -> {
                alertFacade.error("Something went wrong.", this)
            }
        }
    }

    private fun clearSelectedImage(index: Int) {
        when (index) {
            1 -> {
                image_view_selected_image1.setActualImageResource(R.drawable.ic_camera_grey)
                base64StringImage1 = ""

                image_view_delete_image1.visibility = View.GONE
            }
        }
    }

    private fun isLineContactButtonState(isLineContact: Boolean?) {
        when (isLineContact) {
            true -> {
                btn_is_line.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_red)
                btn_is_line.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.white))))
            }
            false -> {
                btn_is_line.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_white)
                btn_is_line.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.kf_red))))

            }
        }
    }

    private fun isMobileContactButtonState(isMobileContact: Boolean?) {
        when (isMobileContact) {
            true -> {
                btn_is_mobile.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_red)
                btn_is_mobile.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.white))))
            }
            false -> {
                btn_is_mobile.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_white)
                btn_is_mobile.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.kf_red))))
            }
        }
    }
}
