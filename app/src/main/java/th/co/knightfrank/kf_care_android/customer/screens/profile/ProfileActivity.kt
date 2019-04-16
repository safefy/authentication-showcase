package th.co.knightfrank.kf_care_android.customer.screens.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.users.GetUserByUserIDRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class ProfileActivity : BaseActivity() {
    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, ProfileActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_profile

    private lateinit var viewModel: ProfileViewModel

    private var mViewPagerAdapter: ProfileViewPagerAdapter? = null
    private var mViewPager: ViewPager? = null

    private fun isCustomer(): Boolean = appSettings.getInt(AppSettingsKey.ROLE_ID) == 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is ProfileViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ProfileViewModel.Status.Success -> {
                    text_view_profile_role_name.text = it.viewDataBundle.userInfoParcel!!._roleInfo!!._roleName

                    mViewPagerAdapter = ProfileViewPagerAdapter(supportFragmentManager,
                            this, it.viewDataBundle.userInfoParcel, isCustomer())
                    mViewPager = findViewById(R.id.view_pager)
                    mViewPager?.adapter = mViewPagerAdapter
                    mViewPagerAdapter?.notifyDataSetChanged()

                    val tabLayout: TabLayout = findViewById(R.id.tab_layout)
                    tabLayout.setupWithViewPager(mViewPager)
                    tabLayoutSetup(tabLayout)
                }

                is ProfileViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is ProfileViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, "Invalid token, Please Login Again!!!")
                    }
                }

                is ProfileViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

    }


    private fun tabLayoutSetup(tabLayout: TabLayout) {
        //set default icon
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_user_profile_red_bg_white)
        if (!isCustomer()) {
            tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_annouce_bg_red)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> tab.setIcon(R.drawable.ic_user_profile_white_bg_red)
                    1 -> tab.setIcon(R.drawable.ic_annouce_bg_red)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> tab.setIcon(R.drawable.ic_user_profile_red_bg_white)
                    1 -> tab.setIcon(R.drawable.ic_annouce_bg_white)
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.getUserByUserIDRequest(
                    GetUserByUserIDRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID)
                    )
            )
        }
    }
}
