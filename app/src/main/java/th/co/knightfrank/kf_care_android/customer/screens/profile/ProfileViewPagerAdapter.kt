package th.co.knightfrank.kf_care_android.customer.screens.profile

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.parceler.Parcels
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel
import th.co.knightfrank.kf_care_android.R


class ProfileViewPagerAdapter(fm: FragmentManager,
                              private val context: Context,
                              private val userInfoParcel: UserInfoParcel,
                              private var isCustomer: Boolean = false) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ProfileInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ProfileInfoFragment.DATA_USER_PROFILE, Parcels.wrap(userInfoParcel))
                }
            }
            1 -> return ProfileNotificationSettingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ProfileInfoFragment.DATA_USER_PROFILE, Parcels.wrap(userInfoParcel))
                }
            }
        }

        return ProfileInfoFragment()
    }

    override fun getCount(): Int {
        return if (isCustomer) {
            1
        } else {
            2
        }
    }


    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.profile_about_you)
            1 -> return context.getString(R.string.profile_notification_setting)
        }
        return ""
    }
}