package th.co.knightfrank.kf_care_android.customer.screens.parcel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_parcel_list.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.parcels.GetParcelListByUserIDRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.parcel.listsectionviewadapter.ParcelListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class ParcelListActivity : BaseActivity() {

    private val parcelListAdapter = ParcelListAdapter(listener = { parcelID ->
        ParcelDetailActivity.start(this, parcelID)
    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, ParcelListActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: ParcelListViewModel

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_parcel_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParcelListViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is ParcelListViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ParcelListViewModel.Status.Success -> {

                    val jobList: MutableList<ParcelListSectionData> = mutableListOf()

                    it.viewDataBundle.parcelList!!.forEach { item ->
                        jobList.add(ParcelListSectionData(item, this))
                    }

                    parcelListAdapter.setItems(jobList)
                }

                is ParcelListViewModel.Status.SuccessWithNoData -> {
                    parcelListAdapter.clear()
                    parcelListAdapter.notifyDataSetChanged()
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                }

                is ParcelListViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is ParcelListViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_parcel_list.adapter = parcelListAdapter
    }

    override fun onResume() {
        super.onResume()

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            //parcelListAdapter.clear()
            viewModel.getParcelListByUserIDRequest(
                    GetParcelListByUserIDRequest(
                            userID = appSettings.getInt(AppSettingsKey.USER_ID),
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN)
                    )
            )
        }
    }
}
