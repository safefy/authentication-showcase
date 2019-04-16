package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment.JobRequestDetailInfoFragment
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment.JobRequestDetailTransactionFragment

class JobRequestDetailViewPagerAdapter(fm: FragmentManager,
                                       private val context: Context,
                                       private val jobRequestID: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return JobRequestDetailInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(JobRequestDetailInfoFragment.DATA_JOB_REQUEST_ID, jobRequestID)
                }
            }
            1 -> return JobRequestDetailTransactionFragment().apply {
                arguments = Bundle().apply {
                    putInt(JobRequestDetailInfoFragment.DATA_JOB_REQUEST_ID, jobRequestID)
                }
            }
        }

        return JobRequestDetailInfoFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.job_request_detail_toolbar_tab_detail)
            1 -> return context.getString(R.string.job_request_detail_toolbar_tab_status)
        }
        return ""
    }

}