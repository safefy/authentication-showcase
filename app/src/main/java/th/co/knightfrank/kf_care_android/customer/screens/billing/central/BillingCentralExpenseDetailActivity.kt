package th.co.knightfrank.kf_care_android.customer.screens.billing.central

import android.app.Activity
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
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_billing_central_expense_detail.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.threeten.bp.format.DateTimeFormatter
import th.co.knightfrank.data_java.data.entities.billings.BillingTypeName
import th.co.knightfrank.data_java.data.entities.billings.PaymentStatusName
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest
import th.co.knightfrank.data_java.data.requests.billings.GetBillingCentralExpenseInvoiceDetailRequest
import th.co.knightfrank.data_java.data.requests.billings.InformPaymentRequest
import th.co.knightfrank.data_java.data.requests.billings.PaymentDataInfoRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.ImageUtilities
import th.co.knightfrank.kf_care_android.customer.components.IntentBuilder
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import java.io.File
import java.math.RoundingMode
import javax.inject.Inject

class BillingCentralExpenseDetailActivity : BaseActivity() {
    companion object {
        private const val DATA_BILLING_CENTRAL_EXPENSE_ID = "DATA_BILLING_CENTRAL_EXPENSE_ID"
        private const val REQ_TAKE_PHOTO_1 = 101
        private const val REQ_IMAGE_1 = 201

        fun start(context: Context, dashboardID: Int? = null) {
            val intent = Intent(context, BillingCentralExpenseDetailActivity::class.java)

            if (dashboardID != null) {
                intent.putExtra(DATA_BILLING_CENTRAL_EXPENSE_ID, dashboardID)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    private lateinit var viewModel: BillingCentralExpenseDetailViewModel

    private val billingCentralExpenseIDData: Int by lazy { intent.getIntExtra(DATA_BILLING_CENTRAL_EXPENSE_ID, 0) }

    private var mCurrentPhotoPath: String = ""
    private var base64StringImage1: String = ""

    private var billingCentralExpenseImageRecyclerViewAdapter: BillingCentralExpenseImageRecyclerViewAdapter? = null

    override fun getLayoutId(): Int = R.layout.activity_billing_central_expense_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BillingCentralExpenseDetailViewModel::class.java)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.getBillingCentralExpenseInvoiceDetailRequest(
                    GetBillingCentralExpenseInvoiceDetailRequest(
                            _token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            _userID = appSettings.getInt(AppSettingsKey.USER_ID),
                            _billingCentralExpenseInvoiceID = billingCentralExpenseIDData
                    )
            )
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is BillingCentralExpenseDetailViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is BillingCentralExpenseDetailViewModel.Status.Success -> {
                    text_view_created_date.text = it.viewDataBundle.billingCentralExpenseInfoResponse?._invoiceDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    text_view_due_date.text = String.format(
                            resources.getString(R.string.billing_due_date),
                            it.viewDataBundle.billingCentralExpenseInfoResponse?._paymentDueDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    )
                    text_view_bill_no.text = String.format(
                            resources.getString(R.string.billing_bill_no),
                            it.viewDataBundle.billingCentralExpenseInfoResponse?._invoiceNo
                    )
//                    text_view_bill_period.text = String.format(
//                            resources.getString(R.string.period),
//                            it.viewDataBundle.billingCentralExpenseInfoResponse?._lastMonthInputDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                            it.viewDataBundle.billingCentralExpenseInfoResponse?._thisMonthInputDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//                    )
                    text_view_room_no.text = String.format(
                            resources.getString(R.string.room_name),
                            it.viewDataBundle.billingCentralExpenseInfoResponse?._roomNo
                    )
                    text_view_room_area.text = String.format(
                            resources.getString(R.string.billing_water_unit),
                            it.viewDataBundle.billingCentralExpenseInfoResponse?._roomArea
                    )
                    text_view_price_per_sqm.text = String.format(
                            resources.getString(R.string.price_baht),
                            it.viewDataBundle.billingCentralExpenseInfoResponse?._pricePerSqm
                    )

                    val netTotalPrice = it.viewDataBundle.billingCentralExpenseInfoResponse?._netTotalPrice
                    val roundedNetTotalPrice = netTotalPrice?.toBigDecimal()?.setScale(2, RoundingMode.HALF_UP)?.toDouble()
                    text_view_price_total.text = String.format(
                            resources.getString(R.string.price_baht),
                            roundedNetTotalPrice
                    )

                    btn_billing_paid.text = String.format(
                            resources.getString(R.string.billing_paid_btn),
                            roundedNetTotalPrice
                    )

                    if (!it.viewDataBundle.billingCentralExpenseInfoResponse?._paymentImageFileName.isNullOrEmpty()) {
                        linear_layout_show_payment_images_container.visibility = View.VISIBLE

                        payment_image_list.layoutManager = GridLayoutManager(this, 1)
                        billingCentralExpenseImageRecyclerViewAdapter = BillingCentralExpenseImageRecyclerViewAdapter(
                                this.let {
                                    val list: MutableList<String>? = mutableListOf()
                                    list?.add(viewModel.currentValue.viewDataBundle.billingCentralExpenseInfoResponse?._paymentImageFileName!!)
                                    return@let list
                                }!!,
                                this
                        )
                        payment_image_list.adapter = billingCentralExpenseImageRecyclerViewAdapter
                    } else {
                        linear_layout_show_payment_images_container.visibility = View.GONE
                    }

                    //Status text
                    when (PaymentStatusName.from(it.viewDataBundle.billingCentralExpenseInfoResponse?._paymentStatus!!)) {
                        PaymentStatusName.OWE -> {
                            text_view_billing_status.text = String.format(
                                    resources.getString(R.string.billing_status),
                                    resources.getString(R.string.billing_status_owe)
                            )
                            text_view_billing_status.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.kf_red))))
                        }
                        PaymentStatusName.PAID -> {
                            text_view_billing_status.text = String.format(
                                    resources.getString(R.string.billing_status),
                                    resources.getString(R.string.billing_status_paid)
                            )
                            text_view_billing_status.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.card_billing_bg))))
                        }
                        PaymentStatusName.BILLED -> {
                            text_view_billing_status.text = String.format(
                                    resources.getString(R.string.billing_status),
                                    resources.getString(R.string.billing_status_billed)
                            )
                            text_view_billing_status.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.green))))
                        }
                        else -> {
                            alertFacade.error("Something went wrong.", this)
                        }
                    }

                    //Image check
                    when (PaymentStatusName.from(it.viewDataBundle.billingCentralExpenseInfoResponse?._paymentStatus!!)) {
                        PaymentStatusName.OWE -> {
                            btn_billing_paid.visibility = View.VISIBLE
                            linear_layout_upload_payment_images_content.visibility = View.VISIBLE
                        }
                        PaymentStatusName.PAID,
                        PaymentStatusName.BILLED -> {
                            btn_billing_paid.visibility = View.GONE
                            linear_layout_upload_payment_images_content.visibility = View.GONE
                        }
                        else -> {
                            alertFacade.error("Something went wrong.", this)
                        }
                    }

                    //IS ADMIN
                    if (RoleName.ADMIN.identifier == appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                        linear_layout_upload_payment_images_content.visibility = View.GONE
                        btn_billing_paid.visibility = View.GONE
                    }
                }

                is BillingCentralExpenseDetailViewModel.Status.SubmitSuccess -> {
                    alertFacade.success(it.viewDataBundle.headerResponse!!._responseDesc!!, this)

                    AlertDialog.Builder(this)
                            .setMessage(resources.getString(R.string.payment_inform_dialog_content))
                            .setPositiveButton(resources.getString(R.string.payment_inform_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                                viewModel.getBillingCentralExpenseInvoiceDetailRequest(
                                        GetBillingCentralExpenseInvoiceDetailRequest(
                                                _token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                                _userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                                _billingCentralExpenseInvoiceID = billingCentralExpenseIDData
                                        )
                                )
                            })
                            .create()
                            .show()
                }

                is BillingCentralExpenseDetailViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is BillingCentralExpenseDetailViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, null)
                    }
                }

                is BillingCentralExpenseDetailViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        val items = arrayOf(getString(R.string.get_picture), getString(R.string.take_photo))
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

        image_view_delete_image1.setOnClickListener {
            clearSelectedImage(1)
        }

        btn_billing_paid.setOnClickListener {
            if (base64StringImage1.isNotEmpty()) {
                viewModel.informPaymentRequest(
                        InformPaymentRequest(
                                _token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                _userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                _paymentData = PaymentDataInfoRequest(
                                        _billingInvoiceDetailID = billingCentralExpenseIDData,
                                        _billingType = BillingTypeName.BILLING_CENTRAL_EXPENSE.identifier,
                                        _imageFile = ImageFileInfoRequest(
                                                fileName = "billing_payment_image_1.jpg",
                                                fileContent = base64StringImage1
                                        )
                                )
                        )
                )
            } else {
                alertFacade.info("Please upload payment image.", this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("onActivityResult", "requestCode : " + requestCode)
        Log.e("onActivityResult", "resultCode : " + resultCode)
        Log.e("onActivityResult", "data : " + data)
        when (requestCode) {
            REQ_IMAGE_1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    processGetImageFromGallery(data.data.toString(), requestCode)
                }
            }
            REQ_TAKE_PHOTO_1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    processCapturedPhoto(requestCode)
                }
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
}
