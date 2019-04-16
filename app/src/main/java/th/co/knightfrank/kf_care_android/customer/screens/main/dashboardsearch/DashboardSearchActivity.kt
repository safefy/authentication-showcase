package th.co.knightfrank.kf_care_android.customer.screens.main.dashboardsearch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_dashboard_search.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.dashboards.DashboardTypeName
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.central.BillingCentralExpenseDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.water.BillingWaterDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.MainAdapter
import th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter.DashboardSectionData
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class DashboardSearchActivity : BaseActivity() {

    private val mainAdapter = MainAdapter(listener = { dashboardID, dashboardTypeName ->
        when (dashboardTypeName) {
            DashboardTypeName.ANNOUNCE.identifier -> {
                AnnouncementDetailActivity.start(this, dashboardID)
            }
            DashboardTypeName.JOB_REQUEST.identifier -> {
                JobRequestDetailActivity.start(this, dashboardID)
                //alertFacade.infoWithView(DashboardTypeName.JOB_REQUEST.identifier + " ID : " + dashboardID, findViewById(R.id.coordinator))
            }
            DashboardTypeName.PARCEL.identifier -> {
                ParcelDetailActivity.start(this, dashboardID)
                //alertFacade.infoWithView(DashboardTypeName.JOB_REQUEST.identifier + " ID : " + dashboardID, findViewById(R.id.coordinator))
            }
            DashboardTypeName.MESSAGE.identifier -> {
                MessageDetailActivity.start(this, dashboardID)
            }
            DashboardTypeName.BILLING_WATER.identifier -> {
                BillingWaterDetailActivity.start(this, dashboardID)
            }
            DashboardTypeName.BILLING_CENTRAL_EXPENSE.identifier -> {
                BillingCentralExpenseDetailActivity.start(this, dashboardID)
            }
            DashboardTypeName.EMPTY.identifier -> {
                alertFacade.infoWithView("Something went wrong.", findViewById(R.id.coordinator))
            }
            else -> {
                alertFacade.infoWithView("Something went wrong.", findViewById(R.id.coordinator))
            }
        }
    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, DashboardSearchActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_dashboard_search

    private lateinit var viewModel: DashboardSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardSearchViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_black)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.inquiryDashboardRequest(
                    InquiryDashboardListRequest(
                            UserID = appSettings.getInt(AppSettingsKey.USER_ID),
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN)
                    )
            )

            image_view_search.setOnClickListener {
                if (edit_text_dashboard_search.text.count() >= 2) {
                    viewModel.filteringDashboardList(
                            text = edit_text_dashboard_search.text.toString()
                    )
                } else {
                    alertFacade.info("Please input name more than 1 character", this)
                }
            }
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is DashboardSearchViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is DashboardSearchViewModel.Status.FilteredSuccess -> {
                    val dashboardList: MutableList<DashboardSectionData> = mutableListOf()
                    it.viewDataBundle.dashboardFilterList!!.forEach { item ->
                        dashboardList.add(DashboardSectionData(item, this))
                    }
                    mainAdapter.setItems(dashboardList)
                }

                is DashboardSearchViewModel.Status.FilteredNoDataFound -> {
                    alertFacade.info(it.message, this)
                }

                is DashboardSearchViewModel.Status.SuccessWithNoData -> {
                    mainAdapter.clear()
                    mainAdapter.notifyDataSetChanged()
                    alertFacade.infoWithView(it.viewDataBundle.headerResponse!!._responseDesc!!, findViewById(R.id.coordinator))
                }

                is DashboardSearchViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.errorWithView("Invalid token, Please Login Again!!!", findViewById(R.id.coordinator))
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is DashboardSearchViewModel.Status.Logout -> {
                    alertFacade.error(it.message, this)
                    appSettings.clearValue(AppSettingsKey.USER_ID)
                    SplashActivity.start(this, null)
                }

                is DashboardSearchViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_dashboard_list.adapter = mainAdapter
    }
}
