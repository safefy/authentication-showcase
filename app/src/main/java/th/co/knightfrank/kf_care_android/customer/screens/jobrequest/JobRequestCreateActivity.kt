package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_job_request_create.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest
import th.co.knightfrank.data_java.data.requests.jobrequests.CreateJobRequestRequest
import th.co.knightfrank.data_java.data.requests.jobrequests.JobDataInfoRequest
import th.co.knightfrank.data_java.data.requests.rooms.GetMasterRoomInfoRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.ImageUtilities
import th.co.knightfrank.kf_care_android.customer.components.IntentBuilder
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class JobRequestCreateActivity : BaseActivity() {

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        private const val REQ_TAKE_PHOTO_1 = 101
        private const val REQ_TAKE_PHOTO_2 = 102
        private const val REQ_TAKE_PHOTO_3 = 103

        private const val REQ_IMAGE_1 = 201
        private const val REQ_IMAGE_2 = 202
        private const val REQ_IMAGE_3 = 203

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, JobRequestCreateActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_job_request_create

    private lateinit var viewModel: JobRequestCreateViewModel

    private var mCurrentPhotoPath: String = ""

    private var base64StringImage1: String = ""
    private var base64StringImage2: String = ""
    private var base64StringImage3: String = ""

    var appointmentDate = ""
    var appointmentTime = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobRequestCreateViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val calendar = Calendar.getInstance()

        val myDateFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(myDateFormat, Locale.US)
        val inputFormat = "yyyy-MM-dd"
        val inputSimpleDateFormat = SimpleDateFormat(inputFormat, Locale.US)

        val myTimeFormat = "HH:mm"
        val simpleTimeFormat = SimpleDateFormat(myTimeFormat, Locale.US)


        val appointmentDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            edit_text_appointment_date.setText(simpleDateFormat.format(calendar.time))
            appointmentDate = inputSimpleDateFormat.format(calendar.time).toString()
        }

        val appointmentTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, hour)

            //appointmentTime = simpleTimeFormat.format(calendar.time).toString()
            appointmentTime = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            edit_text_appointment_time.setText(appointmentTime)
        }

        edit_text_appointment_date?.run {
            setOnClickListener {
                DatePickerDialog(context, appointmentDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
            addTextWatcherListener(this)
        }

        edit_text_appointment_time?.run {
            setOnClickListener {
                TimePickerDialog(context, appointmentTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show()
            }
            addTextWatcherListener(this)
        }
        addTextWatcherListener(edit_text_job_title_input)
        addTextWatcherListener(edit_text_contact_name_input)
        addTextWatcherListener(edit_text_contact_tel_no_input)
        addTextWatcherListener(edit_text_contact_room_no_input)
        addTextWatcherListener(edit_text_job_detail_input)


        val items = arrayOf(getString(R.string.get_picture), getString(R.string.take_photo))

        //initialize
        if (appSettings.getValue(AppSettingsKey.FIRST_NAME).isNotEmpty() && appSettings.getValue(AppSettingsKey.LAST_NAME).isNotEmpty()) {
            edit_text_contact_name_input.setText(appSettings.getValue(AppSettingsKey.FIRST_NAME) + " " + appSettings.getValue(AppSettingsKey.LAST_NAME))
        }
        if (appSettings.getValue(AppSettingsKey.MOBILE_NO).isNotEmpty()) {
            edit_text_contact_tel_no_input.setText(appSettings.getValue(AppSettingsKey.MOBILE_NO))
        }
        if (appSettings.getInt(AppSettingsKey.ROOM_ID) != -1 && appSettings.getValue(AppSettingsKey.LOGIN_TOKEN).isNotEmpty()) {
            viewModel.getMasterRoomInfoRequest(
                    GetMasterRoomInfoRequest(
                            roomID = appSettings.getInt(AppSettingsKey.ROOM_ID),
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN)
                    )
            )
        }

        relative_layout_selected_image1.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.attach))
                    .setItems(items, { dialog, which ->
                        when (items[which]) {
                            getString(R.string.get_picture) -> {
                                val intent = IntentBuilder.imageSelector("Select Image")
                                startActivityForResult(intent, REQ_IMAGE_1)
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

        relative_layout_selected_image2.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.attach))
                    .setItems(items, { dialog, which ->
                        when (items[which]) {
                            getString(R.string.get_picture) -> {
                                val intent = IntentBuilder.imageSelector("Select Image")
                                startActivityForResult(intent, REQ_IMAGE_2)
                            }
                            getString(R.string.take_photo) -> {
                                launchCamera(REQ_TAKE_PHOTO_2)
                            }
                        }
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }

        relative_layout_selected_image3.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.attach))
                    .setItems(items, { dialog, which ->
                        when (items[which]) {
                            getString(R.string.get_picture) -> {
                                val intent = IntentBuilder.imageSelector("Select Image")
                                startActivityForResult(intent, REQ_IMAGE_3)
                            }
                            getString(R.string.take_photo) -> {
                                launchCamera(REQ_TAKE_PHOTO_3)
                            }
                        }
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }

        btn_send_job_request.setOnClickListener {

            if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {

                val imageFileList: MutableList<ImageFileInfoRequest> = mutableListOf()

                if (base64StringImage1.isNotEmpty()) {
                    imageFileList.add(ImageFileInfoRequest(
                            fileName = "job_request_create_image_1.jpg",
                            fileContent = base64StringImage1
                    ))
                }

                if (base64StringImage2.isNotEmpty()) {
                    imageFileList.add(ImageFileInfoRequest(
                            fileName = "job_request_create_image_2.jpg",
                            fileContent = base64StringImage2
                    ))
                }

                if (base64StringImage3.isNotEmpty()) {
                    imageFileList.add(ImageFileInfoRequest(
                            fileName = "job_request_create_image_3.jpg",
                            fileContent = base64StringImage3
                    ))
                }

                if (validateCreateForm()) {
                    viewModel.createJobRequestRequest(
                            CreateJobRequestRequest(
                                    userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                    token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                    jobData = JobDataInfoRequest(
                                            contactName = edit_text_contact_name_input.text.toString().trim(),
                                            contactMobileNo = edit_text_contact_tel_no_input.text.toString().trim(),
                                            contactRoomName = edit_text_contact_room_no_input.text.toString().trim(),
                                            title = edit_text_job_title_input.text.toString().trim(),
                                            detail = edit_text_job_detail_input.text.toString().trim(),
                                            imageFileList = imageFileList,
                                            appointDate = appointmentDate + " " + appointmentTime,
                                            priorityID = null,
                                            jobAreaID = null,
                                            jobSystemTypeID = null,
                                            adminAppointDate = null,
                                            assignToUserID = null
                                    )
                            )
                    )
                }

            } else {
                alertFacade.error("Invalid token, Please Login Again!!!", this)
                SplashActivity.start(this, "Invalid token, Please Login Again!!!")
            }
        }

        image_view_delete_image1.setOnClickListener {
            clearSelectedImage(1)
        }

        image_view_delete_image2.setOnClickListener {
            clearSelectedImage(2)
        }

        image_view_delete_image3.setOnClickListener {
            clearSelectedImage(3)
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is JobRequestCreateViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is JobRequestCreateViewModel.Status.SuccessRoomRequest -> {
                    edit_text_contact_room_no_input.setText(it.viewDataBundle.roomsInfoResponse!!._roomNo)
                }

                is JobRequestCreateViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.headerResponse!!._responseDesc!!, this)

                    AlertDialog.Builder(this)
                            .setTitle(resources.getString(R.string.job_request_create_send_button))
                            .setMessage(resources.getString(R.string.job_request_create_send_success_message_dialog))
                            .setPositiveButton(resources.getString(R.string.job_request_create_send_success_message_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                                this.finish()
                            })
                            .create()
                            .show()
                }

                is JobRequestCreateViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    //this.finish()
                }

                is JobRequestCreateViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, "Invalid token, Please Login Again!!!")
                    }
                }

                is JobRequestCreateViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

    }

    private fun validateCreateForm(): Boolean {
        var hasRequestFocus = false

        var result = true
        if (edit_text_job_title_input.text.isNullOrEmpty()) {
            edit_text_job_title_input?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    requestFocus()
                    scrollView?.smoothScrollTo(0, scrollY)
                    hasRequestFocus = true
                }
            }

            result = false
        }

        if (appointmentDate.isEmpty()) {
            edit_text_appointment_date?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    performClick()
                    scrollView?.smoothScrollTo(0, scrollY)
                    hasRequestFocus = true
                }
            }
            result = false
        }

        if (appointmentTime.isEmpty()) {
            edit_text_appointment_time?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    performClick()
                    scrollView?.smoothScrollTo(0, scrollY)
                    hasRequestFocus = true
                }
            }
            result = false
        }


        if (edit_text_job_detail_input.text.isNullOrEmpty()) {
            edit_text_job_detail_input?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    requestFocus()
                    scrollView?.smoothScrollTo(0, scrollY)
                    hasRequestFocus = true
                }
            }
            result = false
        }


        if (edit_text_contact_name_input.text.isNullOrEmpty()) {
            edit_text_contact_name_input?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    requestFocus()
                    hasRequestFocus = true
                }
            }
            result = false
        }

        if (edit_text_contact_tel_no_input.text.isNullOrEmpty()) {
            edit_text_contact_tel_no_input?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    requestFocus()
                    hasRequestFocus = true
                }
            }
            result = false
        }

        if (edit_text_contact_room_no_input.text.isNullOrEmpty()) {
            edit_text_contact_room_no_input?.run {
                setBackgroundResource(R.drawable.bg_solid_white_round_corner_16dp_red)
                if (!hasRequestFocus) {
                    requestFocus()
                    hasRequestFocus = true
                }
            }
            result = false
        }



        return result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_IMAGE_1, REQ_IMAGE_2, REQ_IMAGE_3 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    processGetImageFromGallery(data.data.toString(), requestCode)
                }
            }
            REQ_TAKE_PHOTO_1, REQ_TAKE_PHOTO_2, REQ_TAKE_PHOTO_3 -> {
                if (resultCode == Activity.RESULT_OK) {
                    processCapturedPhoto(requestCode)
                }
//                else {
//                    Log.e("onActivityResult", "resultCode : " + resultCode)
//                }
            }
        }
    }

    private fun launchCamera(requestFromLayout: Int) {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, requestFromLayout)
        }
    }

    private fun processCapturedPhoto(requestFromLayout: Int) {
        val cursor = contentResolver.query(Uri.parse(mCurrentPhotoPath), Array(1) {
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
                base64StringImage1 = ImageUtilities.encoderJPEGByteArrayToBase64Camera(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image1.visibility = View.VISIBLE
            }
            REQ_TAKE_PHOTO_2 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image2.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image2.controller = controller
                base64StringImage2 = ImageUtilities.encoderJPEGByteArrayToBase64Camera(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image2.visibility = View.VISIBLE
            }
            REQ_TAKE_PHOTO_3 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image3.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image3.controller = controller
                base64StringImage3 = ImageUtilities.encoderJPEGByteArrayToBase64Camera(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image3.visibility = View.VISIBLE
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
                base64StringImage1 = ImageUtilities.encoderJPEGByteArrayToBase64Gallery(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image1.visibility = View.VISIBLE
            }
            REQ_IMAGE_2 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image2.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image2.controller = controller
                base64StringImage2 = ImageUtilities.encoderJPEGByteArrayToBase64Gallery(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image2.visibility = View.VISIBLE
            }
            REQ_IMAGE_3 -> {
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(image_view_selected_image3.controller)
                        .setImageRequest(request)
                        .build()
                image_view_selected_image3.controller = controller
                base64StringImage3 = ImageUtilities.encoderJPEGByteArrayToBase64Gallery(contentResolver, uri, 1280, 720, 100)

                image_view_delete_image3.visibility = View.VISIBLE
            }
            else -> {
                alertFacade.error("Something went wrong.", this)
            }
        }
    }

    private fun clearSelectedImage(index: Int) {
        when (index) {
            1 -> {
//                Uri uri = new Uri.Builder()
//                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
//                        .path(String.valueOf(resId))
//                        .build();
                image_view_selected_image1.setActualImageResource(R.drawable.ic_camera_grey)
                base64StringImage1 = ""

                image_view_delete_image1.visibility = View.GONE
            }
            2 -> {
                image_view_selected_image2.setActualImageResource(R.drawable.ic_camera_grey)
                base64StringImage2 = ""

                image_view_delete_image2.visibility = View.GONE
            }
            3 -> {
                image_view_selected_image3.setActualImageResource(R.drawable.ic_camera_grey)
                base64StringImage3 = ""

                image_view_delete_image3.visibility = View.GONE
            }

        }
    }

    fun addTextWatcherListener(view: EditText?) {
        view?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    view?.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_white)
                }
            }

        })
    }

}
