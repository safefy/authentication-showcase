package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_job_request_list.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.jobrequests.GetJobRequestByUserIDRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobcreate.CreateJobRequestByAdminActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.listsectionviewadapter.JobRequestListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class JobRequestListActivity : BaseActivity() {

    private val jobRequestListAdapter = JobRequestListAdapter(listener = { jobRequestID ->
        JobRequestDetailActivity.start(this, jobRequestID)
    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, JobRequestListActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_job_request_list

    private lateinit var viewModel: JobRequestListViewModel

    private var thisActivity = this

    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobRequestListViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar_add.setOnClickListener({ _ -> startJobRequestCreateActivity() })

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.job_request_list_toolbar_tab_waiting_approve), true)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.job_request_list_toolbar_tab_all))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        loadDataFromTabSelected(tab.position)
                    }

                    1 -> {
                        loadDataFromTabSelected(tab.position)
                    }
                }
            }

        })


        viewModel.status.observe(this, Observer {
            when (it) {
                is JobRequestListViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is JobRequestListViewModel.Status.Success -> {

                    val jobList: MutableList<JobRequestListSectionData> = mutableListOf()

                    it.viewDataBundle.jobRequestList!!.forEach { item ->
                        jobList.add(JobRequestListSectionData(item, this))
                    }

                    jobRequestListAdapter.setItems(jobList)
                }

                is JobRequestListViewModel.Status.SuccessWithNoData -> {
                    jobRequestListAdapter.clear()
                    jobRequestListAdapter.notifyDataSetChanged()
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                }

                is JobRequestListViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is JobRequestListViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_job_request_list.adapter = jobRequestListAdapter
    }

    override fun onResume() {
        super.onResume()

        loadDataFromTabSelected(tab_layout.selectedTabPosition)
    }

    private fun loadDataFromTabSelected(currentTab: Int) {
        Log.e("loadDataFromTabSelected", "tab selected : " + currentTab)

        when (currentTab) {
            0 -> {
                if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                    //jobRequestListAdapter.clear()
                    viewModel.getJobRequestByUserIDRequest(
                            GetJobRequestByUserIDRequest(
                                    userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                    token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                    jobStatus = arrayListOf(4)
                            )
                    )
                }
            }
            1 -> {
                if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                    //jobRequestListAdapter.clear()
                    viewModel.getJobRequestByUserIDRequest(
                            GetJobRequestByUserIDRequest(
                                    userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                    token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                    jobStatus = arrayListOf(1, 2, 3, 4, 5, 6)
                            )
                    )
                }
            }
        }
    }

    private fun startJobRequestCreateActivity() {
        if (appSettings.getInt(AppSettingsKey.ROLE_ID) != -1) {
            when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                RoleName.SYSTEM_ADMIN.identifier,
                RoleName.MANAGER.identifier,
                RoleName.ASSISTANT_MANAGER.identifier,
                RoleName.ADMIN.identifier,
                RoleName.TECHNICIAN_MANAGER.identifier,
                RoleName.TECHNICIAN.identifier -> {
                    CreateJobRequestByAdminActivity.start(this)
                }
                RoleName.CUSTOMER.identifier -> {
                    JobRequestCreateActivity.start(this)
                }
            }
        }

    }
}
