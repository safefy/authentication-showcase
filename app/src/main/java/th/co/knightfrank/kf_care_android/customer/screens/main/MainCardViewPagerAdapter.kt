package th.co.knightfrank.kf_care_android.customer.screens.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.parceler.Parcels
import th.co.knightfrank.kf_care_android.customer.models.dashboards.DashboardPinModel


class MainCardViewPagerAdapter(fm: FragmentManager,
                               private val dashboardPinList: List<DashboardPinModel>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return MainCardFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MainCardFragment.DATA_DASHBOARD_PIN, Parcels.wrap(dashboardPinList[position]))
            }
        }
    }

    override fun getCount(): Int = dashboardPinList.size

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}


//import android.content.Context
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentManager
//import android.support.v4.app.FragmentPagerAdapter
//import android.support.v4.view.PagerAdapter
//import org.parceler.Parcels
//import th.co.knightfrank.kf_care_android.customer.models.dashboards.DashboardPinModel
//
//class MainCardViewPagerAdapter(fm: FragmentManager,
//                               private val context: Context,
//                               private val dashboardPinList: List<DashboardPinModel>) : FragmentPagerAdapter(fm) {
//    override fun getItem(position: Int): Fragment {
//        return MainCardFragment().apply {
//            arguments = Bundle().apply {
//                putParcelable(MainCardFragment.DATA_DASHBOARD_PIN, Parcels.wrap(dashboardPinList[position]))
//            }
//        }
//    }
//
//    override fun getCount(): Int = dashboardPinList.size
//
//    override fun getItemPosition(`object`: Any): Int {
//        return PagerAdapter.POSITION_NONE
//    }
//
//}

