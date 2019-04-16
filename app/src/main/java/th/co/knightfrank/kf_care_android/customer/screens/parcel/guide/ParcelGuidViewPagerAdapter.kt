package th.co.knightfrank.kf_care_android.customer.screens.parcel.guide

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ParcelGuidViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        return ParcelGuideFragment().apply {
            arguments = Bundle().apply {
                putInt(ParcelGuideFragment.DATA_PARCEL_GUIDE_POSITION, position)
            }
        }
    }

    override fun getCount(): Int = 3

}