package th.co.knightfrank.kf_care_android.customer.screens.announcement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_announcement_list.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.SearchFilterInfoRequest
import th.co.knightfrank.data_java.data.requests.announcements.SearchAnnouncementRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.announcement.listsectionviewadapter.AnnouncementListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class AnnouncementListActivity : BaseActivity() {

    private val announcementListAdapter = AnnouncementListAdapter(listener = { announceID ->
        AnnouncementDetailActivity.start(this, announceID)
    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, AnnouncementListActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: AnnouncementListViewModel

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_announcement_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AnnouncementListViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (appSettings.getInt(AppSettingsKey.ROLE_ID) != -1) {
            when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                RoleName.SYSTEM_ADMIN.identifier,
                RoleName.MANAGER.identifier,
                RoleName.ASSISTANT_MANAGER.identifier,
                RoleName.ADMIN.identifier -> {
                    toolbar_add.visibility = View.VISIBLE
                }
                RoleName.TECHNICIAN_MANAGER.identifier,
                RoleName.TECHNICIAN.identifier,
                RoleName.CUSTOMER.identifier -> {
                    toolbar_add.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is AnnouncementListViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is AnnouncementListViewModel.Status.Success -> {

                    val jobList: MutableList<AnnouncementListSectionData> = mutableListOf()

                    it.viewDataBundle.announcementList!!.forEach { item ->
                        jobList.add(AnnouncementListSectionData(item, this))
                    }

                    announcementListAdapter.setItems(jobList)
                }

                is AnnouncementListViewModel.Status.SuccessWithNoData -> {
                    announcementListAdapter.clear()
                    announcementListAdapter.notifyDataSetChanged()
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                }

                is AnnouncementListViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is AnnouncementListViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_announcement_list.adapter = announcementListAdapter

    }

    override fun onResume() {
        super.onResume()

        //Main Content
        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.searchAnnouncementRequest(
                    SearchAnnouncementRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            searchFilter = SearchFilterInfoRequest()
                    )
            )
        }
    }
}
