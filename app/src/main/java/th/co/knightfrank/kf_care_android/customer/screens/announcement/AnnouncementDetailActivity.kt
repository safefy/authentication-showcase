package th.co.knightfrank.kf_care_android.customer.screens.announcement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_announcement_detail.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.threeten.bp.format.DateTimeFormatter
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.announcements.AcceptAnnouncementRequest
import th.co.knightfrank.data_java.data.requests.announcements.GetAnnouncementDetailRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.announcement.edit.AnnouncementEditActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class AnnouncementDetailActivity : BaseActivity() {

    companion object {
        private const val DATA_ANNOUNCE_ID = "DATA_ANNOUNCE_ID"

        fun start(context: Context, dashboardID: Int? = null) {
            val intent = Intent(context, AnnouncementDetailActivity::class.java)

            if (dashboardID != null) {
                intent.putExtra(DATA_ANNOUNCE_ID, dashboardID)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_announcement_detail

    private val announceIDData: Int by lazy { intent.getIntExtra(DATA_ANNOUNCE_ID, 0) }
    private lateinit var viewModel: AnnouncementDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AnnouncementDetailViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is AnnouncementDetailViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is AnnouncementDetailViewModel.Status.Success -> {
                    text_view_announcement_title.text = it.viewDataBundle.announcementInfoParcel!!._title
                    text_view_created_date.text = it.viewDataBundle.announcementInfoParcel._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    text_view_between_date.text = String.format(
                            resources.getString(R.string.announcement_between_date),
                            it.viewDataBundle.announcementInfoParcel._startDateDisplay,
                            it.viewDataBundle.announcementInfoParcel._endDateDisplay
                    )
                    text_view_location.text = it.viewDataBundle.announcementInfoParcel._location
                    text_view_announcement_detail.text = it.viewDataBundle.announcementInfoParcel._detail

                    if (it.viewDataBundle.announcementInfoParcel._imageFileList!!.isNotEmpty()) {
                        image_view_announcement_image.visibility = View.VISIBLE
                        image_view_announcement_image.setImageURI(it.viewDataBundle.announcementInfoParcel._imageFileList!![0])
                    } else {
                        image_view_announcement_image.visibility = View.GONE
                    }

                    when (it.viewDataBundle.announcementInfoParcel._isRequireAccept) {
                        true -> {
                            when (it.viewDataBundle.announcementInfoParcel._isAccepted) {
                                true -> {
                                    linear_layout_is_accepted_container.visibility = View.GONE
                                    text_view_is_accepted.text = resources.getText(R.string.announcement_text_accepted)
                                }
                                false -> {
                                    linear_layout_is_accepted_container.visibility = View.GONE
                                    text_view_is_accepted.text = resources.getText(R.string.announcement_text_not_accepted)
                                }
                                null -> {
                                    linear_layout_is_accepted_container.visibility = View.VISIBLE
                                    button_accepted.visibility = View.VISIBLE
                                    button_not_accepted.visibility = View.VISIBLE
                                    button_accepted.isClickable = true
                                    button_not_accepted.isClickable = true
                                    text_view_is_accepted.text = resources.getText(R.string.announcement_text_waiting_accepted)
                                }
                            }
                        }
                        false -> {
                            linear_layout_is_accepted_container.visibility = View.GONE
                        }
                    }
                }

                is AnnouncementDetailViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is AnnouncementDetailViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, null)
                    }
                }

                is AnnouncementDetailViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        button_not_accepted.setOnClickListener {
            if (appSettings.getInt(AppSettingsKey.USER_ID) != -1 && announceIDData != 0) {
                viewModel.acceptAnnouncementRequest(
                        AcceptAnnouncementRequest(
                                token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                announceID = announceIDData,
                                userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                isAccept = false
                        ),
                        viewModel.currentValue.viewDataBundle.announcementInfoParcel!!
                )
            }
        }

        button_accepted.setOnClickListener {
            if (appSettings.getInt(AppSettingsKey.USER_ID) != -1 && announceIDData != 0) {
                viewModel.acceptAnnouncementRequest(
                        AcceptAnnouncementRequest(
                                token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                announceID = announceIDData,
                                userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                isAccept = true
                        ),
                        viewModel.currentValue.viewDataBundle.announcementInfoParcel!!
                )
            }
        }

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                RoleName.SYSTEM_ADMIN.identifier,
                RoleName.MANAGER.identifier,
                RoleName.ASSISTANT_MANAGER.identifier,
                RoleName.ADMIN.identifier -> {
                    linear_layout_edit_announcement_container.visibility = View.VISIBLE
                    btn_edit_announcement.setOnClickListener {
                        AnnouncementEditActivity.start(this, announceIDData)
                    }
                }
                else -> {
                    linear_layout_edit_announcement_container.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1 && announceIDData != 0) {
            viewModel.getAnnouncementDetailRequest(
                    GetAnnouncementDetailRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            announceID = announceIDData
                    )
            )
        }
    }
}
