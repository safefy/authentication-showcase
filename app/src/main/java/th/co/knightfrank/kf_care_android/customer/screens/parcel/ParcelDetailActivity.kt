package th.co.knightfrank.kf_care_android.customer.screens.parcel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_parcel_detail.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.threeten.bp.format.DateTimeFormatter
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.parcels.GetParcelDetailRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.parcel.checkout.ParcelCheckoutActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.parcel.guide.ParcelGuideActivity
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class ParcelDetailActivity : BaseActivity() {

    companion object {
        private const val DATA_PARCEL_ID = "DATA_PARCEL_ID"

        fun start(context: Context, dashboardID: Int? = null) {
            val intent = Intent(context, ParcelDetailActivity::class.java)

            if (dashboardID != null) {
                intent.putExtra(DATA_PARCEL_ID, dashboardID)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    private val parcelIDData: Int by lazy { intent.getIntExtra(DATA_PARCEL_ID, 0) }
    private lateinit var viewModel: ParcelDetailViewModel

    override fun getLayoutId(): Int = R.layout.activity_parcel_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParcelDetailViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is ParcelDetailViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ParcelDetailViewModel.Status.Success -> {
                    //text_view_parcel_title.text = it.viewDataBundle.parcelDataInfo?._parcelNote
                    text_view_parcel_title.text = it.viewDataBundle.parcelData?._parcelNo
                    text_view_created_date.text = it.viewDataBundle.parcelData?._registerDate!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    text_view_parcel_provider_name.text = String.format(
                            resources.getString(R.string.parcel_shipped_by),
                            it.viewDataBundle.parcelData._parcelProviderName
                    )

                    text_view_parcel_no.text = it.viewDataBundle.parcelData._parcelNo
                    text_view_parcel_note.text = String.format(
                            resources.getString(R.string.parcel_note),
                            it.viewDataBundle.parcelData._parcelNote
                    )

//                    text_view_status.text = it.viewDataBundle.parcelDataInfo._isReceive.let {
//                        when(it!!){
//                            true -> {
//                                resources.getString(R.string.parcel_detail_status_received)
//
//                            }
//                            false -> resources.getString(R.string.parcel_detail_status_waiting_receive)
//                        }
//                    }

                    when (it.viewDataBundle.parcelData._isReceive) {
                        true -> {
                            text_view_status.text = resources.getString(R.string.parcel_detail_status_received)
                            text_view_parcel_receive_status.text = String.format(
                                    resources.getString(R.string.parcel_detail_status),
                                    resources.getString(R.string.parcel_detail_status_received)
                                            + " "
                                            + it.viewDataBundle.parcelData._checkoutDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            )
                            image_view_receive_status.setImageResource(R.drawable.ic_approve_bg_green)
                            btn_goto_checkout.visibility = View.GONE
                        }
                        false -> {
                            text_view_status.text = resources.getString(R.string.parcel_detail_status_waiting_receive)
                            text_view_parcel_receive_status.text = String.format(
                                    resources.getString(R.string.parcel_detail_status),
                                    resources.getString(R.string.parcel_detail_status_waiting_receive)
                            )
                            image_view_receive_status.setImageResource(R.drawable.ic_parcel_bg_orange)

                            when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                                RoleName.SYSTEM_ADMIN.identifier,
                                RoleName.MANAGER.identifier,
                                RoleName.ASSISTANT_MANAGER.identifier,
                                RoleName.ADMIN.identifier -> {
                                    btn_goto_checkout.visibility = View.VISIBLE
                                }
                                else -> {
                                    btn_goto_checkout.visibility = View.GONE
                                }
                            }
                        }
                    }

                    if (it.viewDataBundle.parcelData._imageFileList!!.isNotEmpty()) {
                        image_view_parcel_image.visibility = View.VISIBLE
                        image_view_parcel_image.setImageURI(it.viewDataBundle.parcelData._imageFileList!![0])
                    } else {
                        image_view_parcel_image.visibility = View.GONE
                    }

                    btn_goto_checkout.setOnClickListener { _ -> startParcelCheckOutActivity(it.viewDataBundle.parcelData._parcelID) }
                }

                is ParcelDetailViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is ParcelDetailViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, null)
                    }
                }

                is ParcelDetailViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        btn_goto_guide.setOnClickListener { _ -> startParcelGuideActivity() }

    }

    fun startParcelGuideActivity() {
        ParcelGuideActivity.start(this)
    }

    fun startParcelCheckOutActivity(parcelID: Int?) {
        ParcelCheckoutActivity.start(
                this,
                parcelID
        )
    }

    override fun onResume() {
        super.onResume()

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1 && parcelIDData != 0) {
            viewModel.getParcelDetailRequest(
                    GetParcelDetailRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID),
                            _parcelID = parcelIDData
                    )
            )
        }
    }

}
