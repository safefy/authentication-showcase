package th.co.knightfrank.kf_care_android.customer.screens.main

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.itsronald.widget.ViewPagerIndicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import th.co.knightfrank.data_java.data.entities.billings.BillingTypeName
import th.co.knightfrank.data_java.data.entities.dashboards.DashboardTypeName
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.billing.list.BillingListActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.models.dashboards.DashboardPinModel
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementListActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.central.BillingCentralExpenseDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.water.BillingWaterDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestCreateActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestListActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.dashboardsearch.DashboardSearchActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter.DashboardSectionData
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageCreateActivity
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageListActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelListActivity
import th.co.knightfrank.kf_care_android.customer.screens.profile.ProfileActivity
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.ChangeUserRoomListActivity
import th.co.knightfrank.kf_care_android.customer.screens.settings.SettingsActivity
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.screens.workorder.WorkOrderWebViewActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

@RuntimePermissions
class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val mainAdapter = MainAdapter(listener = { dashboardID, dashboardTypeName ->
        //viewModel.faqsSelect(items)
        //Log.e("mainAdapterClicked","ID : " + items._announceID.toString())

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
                //BillingWaterDetailActivity.start(this, dashboardID)
                goToBillingDetailWithPermissionCheck(dashboardID, BillingTypeName.BILLING_WATER.identifier)
            }
            DashboardTypeName.BILLING_CENTRAL_EXPENSE.identifier -> {
                //BillingCentralExpenseDetailActivity.start(this, dashboardID)
                goToBillingDetailWithPermissionCheck(dashboardID, BillingTypeName.BILLING_CENTRAL_EXPENSE.identifier)
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
            val intent = Intent(context, MainActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_main

    private lateinit var viewModel: MainViewModel

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private var mSectionPagerAdapter: MainCardViewPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    private var mViewPagerIndicator: ViewPagerIndicator? = null

    private var mOnPageChangedListener: ViewPager.OnPageChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        //Navigation Drawer Content
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val toolbarSearch = toolbar.findViewById<ImageView>(R.id.toolbar_search)
        toolbarSearch.setOnClickListener {
            DashboardSearchActivity.start(this, null)
        }

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

//        nav_view.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_item_dashboard -> {
//                    alertFacade.error("nav_item_dashboard", this)
//                }
//                R.id.nav_item_profile -> {
//                    alertFacade.error("nav_item_profile", this)
//                }
//            }
//
//            // Close the drawer
//            drawer_layout.closeDrawer(GravityCompat.START)
//            true
//        }

        //image_view_test.setImageURI("http://res.cloudinary.com/demo/image/upload/f_auto,q_auto/pond_reflect.jpg")

        val headerView: View = nav_view.getHeaderView(0)
        val headerViewUsername = headerView.findViewById<TextView>(R.id.nav_header_user_first_name_text)
        val headerViewRoomNo = headerView.findViewById<TextView>(R.id.nav_header_user_room_no_text)
        val headerViewImageProfile = headerView.findViewById<SimpleDraweeView>(R.id.image_view)
        headerViewUsername.text = appSettings.getValue(AppSettingsKey.FIRST_NAME)
        Log.e("Firstname", "FirstName : " + appSettings.getValue(AppSettingsKey.FIRST_NAME))
        headerViewRoomNo.text = appSettings.getValue(AppSettingsKey.ROOM_NO)


        if (appSettings.getValue(AppSettingsKey.IMAGE_PATH).isNotEmpty()) {
            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(appSettings.getValue(AppSettingsKey.IMAGE_PATH)))
                    .setResizeOptions(ResizeOptions(1280, 720))
                    .build()

            val controller = Fresco.newDraweeControllerBuilder()
                    .setTapToRetryEnabled(true)
                    .setOldController(headerViewImageProfile.controller)
                    .setImageRequest(request)
                    .build()

            headerViewImageProfile.controller = controller
        }

        //menu click
        val image_view_menu_parcels = headerView.findViewById<ImageView>(R.id.image_view_menu_parcels)
        val image_view_menu_billings = headerView.findViewById<ImageView>(R.id.image_view_menu_billings)
        val image_view_menu_announcements = headerView.findViewById<ImageView>(R.id.image_view_menu_announcements)
        val image_view_menu_job_request = headerView.findViewById<ImageView>(R.id.image_view_menu_job_request)
        val image_view_menu_contact = headerView.findViewById<ImageView>(R.id.image_view_menu_contact)
        val linear_layout_menu_dashboard = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_dashboard)
        val linear_layout_menu_profile = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_profile)
        val linear_layout_menu_work_order = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_work_order)
        val linear_layout_menu_settigs = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_settings)
        val linear_layout_menu_logout = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_logout)
        val linear_layout_menu_create_job_request = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_create_job_request)
        val linear_layout_menu_create_message = headerView.findViewById<LinearLayout>(R.id.linear_layout_menu_create_message)

        headerViewRoomNo.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            ChangeUserRoomListActivity.start(this, null)
        }

        image_view_menu_parcels.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            ParcelListActivity.start(this, null)
        }

        image_view_menu_billings.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            BillingListActivity.start(this, null)
        }

        image_view_menu_announcements.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            AnnouncementListActivity.start(this, null)
        }

        image_view_menu_job_request.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            JobRequestListActivity.start(this, null)
        }

        image_view_menu_contact.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            //alertFacade.infoWithView("ยังไม่สามารถใช้งานได้", findViewById(R.id.coordinator))
            MessageListActivity.start(this, null)
        }


        linear_layout_menu_dashboard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.startActivity(intent)
        }

        linear_layout_menu_profile.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            ProfileActivity.start(this, null)
            //alertFacade.info("ยังไม่สามารถใช้งานได้", this)
        }

        linear_layout_menu_work_order.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            WorkOrderWebViewActivity.start(this, null)
        }

        linear_layout_menu_settigs.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            SettingsActivity.start(this, null)
        }

        linear_layout_menu_create_job_request.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            goToJobRequestWithPermissionCheck()
        }

        linear_layout_menu_create_message.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            goToMessageCreateWithPermissionCheck()
        }

        linear_layout_menu_logout.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)

            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.logout_confirm_title))
                    .setPositiveButton(resources.getString(R.string.logout_confirm_accept), { dialog, _ ->
                        dialog.dismiss()
                        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                            viewModel.logout()
                        }
                    })
                    .setNegativeButton(resources.getString(R.string.logout_confirm_cancel), { dialog, _ ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }

        callData()

        swipe_layout.setOnRefreshListener(this)

        fab_button.setOnClickListener {
            //forceCrash()
            goToJobRequestWithPermissionCheck()
        }


        //Get the token
        //Use the token only for received a push notification this device
//        var token = FirebaseInstanceId.getInstance().token
//        Log.e("MainActivity","Token : "+token)
//
//        var bodyMessage = intent.getStringExtra("Notification")
//        if (!bodyMessage.isNullOrEmpty()) {
//            Log.e("MainActivity","bodyMessage : " + bodyMessage)
//        }

    }

    override fun onRefresh() {
        val currentPosition = mViewPager?.currentItem
        swipe_layout.isRefreshing = false
        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.inquiryDashboardRequest(
                    InquiryDashboardListRequest(
                            UserID = appSettings.getInt(AppSettingsKey.USER_ID),
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN)
                    )
            )
        }
        callData()
        mViewPager?.postDelayed({
            mViewPager?.setCurrentItem(currentPosition!!, true)
        }, 500)
    }

    private fun callData() {
        viewModel.status.observe(this, Observer {
            when (it) {
                is MainViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is MainViewModel.Status.Success -> {
                    //Log.e("MainActivity", "success")
                    //alertFacade.success(it.viewDataBundle.status_msg!!, this)
                    //alertFacade.success(it.viewDataBundle.dashboardItemList!!.get(1)._announceID.toString(), this)
                    //goto mainActivity
                    //start(this)

                    //viewpager setup
                    val dashboardPinList: MutableList<DashboardPinModel> = mutableListOf()
                    it.viewDataBundle.dashboardPinList?.forEach { item ->
                        dashboardPinList.add(DashboardPinModel(
                                _pinText = item._pinText,
                                _pinCount = item._pinCount,
                                _pinUnitText = item._pinUnitText,
                                _pinType = item._pinType,
                                _detail = item._detail,
                                _bgColor = item._bgColor
                        ))
                    }

                    //mSectionPagerAdapter = MainCardViewPagerAdapter(supportFragmentManager, this, dashboardPinList)
                    mSectionPagerAdapter = MainCardViewPagerAdapter(supportFragmentManager, dashboardPinList)
                    mViewPager = findViewById(R.id.view_pager)
                    mViewPager?.adapter = mSectionPagerAdapter

                    mSectionPagerAdapter?.notifyDataSetChanged()

                    val dashboardList: MutableList<DashboardSectionData> = mutableListOf()

                    dashboardList.clear()
                    it.viewDataBundle.dashboardItemList!!.forEach { item ->
                        //mainAdapter.addItem(DashboardSectionData(item, this))
                        dashboardList.add(DashboardSectionData(item, this@MainActivity))
                    }
                    mainAdapter.setItems(dashboardList)

                    mViewPager?.setBackgroundResource(R.color.card_all_bg)

                    mOnPageChangedListener = object : ViewPager.OnPageChangeListener {
                        override fun onPageScrollStateChanged(state: Int) {

                        }

                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                        }

                        override fun onPageSelected(position: Int) {
                            when (position) {
                                0 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_all_bg)

                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                                1 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_job_request_bg)
                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        if (item._dashboardTypeName?.contains(DashboardTypeName.JOB_REQUEST.identifier)!!) {
                                            dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                        }
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                                2 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_announcement_bg)
                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        if (item._dashboardTypeName?.contains(DashboardTypeName.ANNOUNCE.identifier)!!) {
                                            dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                        }
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                                3 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_parcels_bg)
                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        if (item._dashboardTypeName?.contains(DashboardTypeName.PARCEL.identifier)!!) {
                                            dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                        }
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                                4 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_billing_bg)
                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        if (item._dashboardTypeName?.contains(DashboardTypeName.BILLING.identifier)!!) {
                                            dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                        }
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                                5 -> {
                                    mViewPager?.setBackgroundResource(R.color.card_message_bg)
                                    dashboardList.clear()
                                    it.viewDataBundle.dashboardItemList.forEach { item ->
                                        if (item._dashboardTypeName?.contains(DashboardTypeName.MESSAGE.identifier)!!) {
                                            dashboardList.add(DashboardSectionData(item, this@MainActivity))
                                        }
                                    }
                                    mainAdapter.setItems(dashboardList)
                                }
                            }
                        }

                    }

                    mViewPager?.addOnPageChangeListener(mOnPageChangedListener!!)
                }

                is MainViewModel.Status.SuccessWithNoData -> {
                    mainAdapter.clear()
                    mainAdapter.notifyDataSetChanged()
                    alertFacade.infoWithView(it.viewDataBundle.headerResponse!!._responseDesc!!, findViewById(R.id.coordinator))
                }

                is MainViewModel.Status.Error -> {
                    Log.e("MainActivity", "error : " + it.viewDataBundle.headerResponse!!._responseCode)
                    //alertFacade.error(it.viewDataBundle.status_msg!!, this)

                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.errorWithView("Invalid token, Please Login Again!!!", findViewById(R.id.coordinator))
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is MainViewModel.Status.Logout -> {
                    Log.e("MainActivity", "Logout")
                    alertFacade.errorWithView(it.message, findViewById(R.id.coordinator))
                    appSettings.clearValue(AppSettingsKey.USER_ID)
                    SplashActivity.start(this, null)
                    //LoginActivity.start(this,null)
                }

                is MainViewModel.Status.ErrorException -> {
                    alertFacade.errorWithView(it.message, findViewById(R.id.coordinator))
                }
            }
        })

        recycler_view_dashboard_list.adapter = mainAdapter
    }

    override fun onResume() {
        super.onResume()
        //Log.e("MainActivity", "onResume Start")

        mViewPager?.setCurrentItem(0, true)

        Log.d("MainActivity", "OnResume Work!!")

        //Main Content
        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
//            Log.e("MainActivity", "USERID : " + appSettings.getInt(AppSettingsKey.USER_ID).toString())
//            Log.e("MainActivity", "LOGIN_TOKEN : " + appSettings.getValue(AppSettingsKey.LOGIN_TOKEN))

            viewModel.inquiryDashboardRequest(
                    InquiryDashboardListRequest(
                            UserID = appSettings.getInt(AppSettingsKey.USER_ID),
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN)
                    )
            )

            val headerView: View = nav_view.getHeaderView(0)
            val headerViewUsername = headerView.findViewById<TextView>(R.id.nav_header_user_first_name_text)
            val headerViewRoomNo = headerView.findViewById<TextView>(R.id.nav_header_user_room_no_text)
            val headerViewImageProfile = headerView.findViewById<SimpleDraweeView>(R.id.image_view)
            headerViewUsername.text = appSettings.getValue(AppSettingsKey.FIRST_NAME)
            Log.e("Firstname", "FirstName : " + appSettings.getValue(AppSettingsKey.FIRST_NAME))
            headerViewRoomNo.text = appSettings.getValue(AppSettingsKey.ROOM_NO)


            if (appSettings.getValue(AppSettingsKey.IMAGE_PATH).isNotEmpty()) {
                val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(appSettings.getValue(AppSettingsKey.IMAGE_PATH)))
                        .setResizeOptions(ResizeOptions(1280, 720))
                        .build()

                val controller = Fresco.newDraweeControllerBuilder()
                        .setTapToRetryEnabled(true)
                        .setOldController(headerViewImageProfile.controller)
                        .setImageRequest(request)
                        .build()

                headerViewImageProfile.controller = controller
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToJobRequest() {
        JobRequestCreateActivity.start(this, null)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToMessageCreate() {
        MessageCreateActivity.start(this, null)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToBillingDetail(id: Int, billingType: Int) {
        when (BillingTypeName.from(billingType)) {
            BillingTypeName.BILLING_WATER -> {
                BillingWaterDetailActivity.start(this, id)
            }
            BillingTypeName.BILLING_CENTRAL_EXPENSE -> {
                BillingCentralExpenseDetailActivity.start(this, id)
            }
            else -> {

            }
        }
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun readPermissionDenied() {
        alertFacade.error("OnPermissionDenied", this)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun neverAskReadPermissionAgain() {
        alertFacade.error("OnNeverAskAgain", this)
    }


}