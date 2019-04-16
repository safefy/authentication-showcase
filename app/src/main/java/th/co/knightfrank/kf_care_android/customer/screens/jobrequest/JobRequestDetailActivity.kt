package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import th.co.knightfrank.kf_care_android.customer.ui.views.nonswipeableviewpager.NonSwipeableViewPager

class JobRequestDetailActivity : BaseActivity() {

    companion object {
        private const val DATA_JOB_REQUEST_ID = "DATA_JOB_REQUEST_ID"

        fun start(context: Context, dashboardID: Int? = null) {
            val intent = Intent(context, JobRequestDetailActivity::class.java)

            if (dashboardID != null) {
                intent.putExtra(DATA_JOB_REQUEST_ID, dashboardID)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_job_request_detail

    private val jobRequestIDData: Int by lazy { intent.getIntExtra(DATA_JOB_REQUEST_ID, 0) }

    private var mSectionPagerAdapter: JobRequestDetailViewPagerAdapter? = null
    private var mViewPager: NonSwipeableViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mSectionPagerAdapter = JobRequestDetailViewPagerAdapter(supportFragmentManager, this, jobRequestIDData)
        mViewPager = findViewById<NonSwipeableViewPager?>(R.id.view_pager)
        mViewPager!!.adapter = mSectionPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(mViewPager)
        tabLayoutSetup(tabLayout)
    }

    private fun tabLayoutSetup(tabLayout: TabLayout) {
        //set default icon
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_document_blue_bg_white_with_circle)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_clock_white_with_circle)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_document_white_with_circle)
                    }

                    1 -> {
                        tab.setIcon(R.drawable.ic_clock_white_with_circle)
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_document_blue_bg_white_with_circle)
                    }

                    1 -> {
                        tab.setIcon(R.drawable.ic_clock_blue_bg_white_with_circle)
                    }
                }
            }

        })
    }

}
