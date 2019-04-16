package th.co.knightfrank.kf_care_android.customer.screens.profile

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile_notification_setting.*
import org.parceler.Parcels
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel

import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment

class ProfileNotificationSettingFragment : BaseFragment() {

    companion object {
        const val DATA_USER_PROFILE = "DATA_USER_PROFILE"
    }

    private val userInfoParcel: UserInfoParcel
        get() = Parcels.unwrap<UserInfoParcel>(arguments?.getParcelable(DATA_USER_PROFILE))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_notification_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLineContactButtonState(userInfoParcel._isContactLine)
        isMobileContactButtonState(userInfoParcel._isContactMobile)
    }

    private fun isLineContactButtonState(isLineContact: Boolean?) {
        when (isLineContact) {
            true -> {
                btn_is_line.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_red)
                btn_is_line.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.white))))
            }
            false -> {
                btn_is_line.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_white)
                btn_is_line.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.kf_red))))

            }
        }
    }

    private fun isMobileContactButtonState(isMobileContact: Boolean?) {
        when (isMobileContact) {
            true -> {
                btn_is_mobile.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_red)
                btn_is_mobile.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.white))))
            }
            false -> {
                btn_is_mobile.setBackgroundResource(R.drawable.shape_button_round_corner_red_bg_white)
                btn_is_mobile.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.kf_red))))
            }
        }
    }
}
