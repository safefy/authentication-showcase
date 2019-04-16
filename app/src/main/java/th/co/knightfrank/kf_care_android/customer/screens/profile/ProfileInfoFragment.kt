package th.co.knightfrank.kf_care_android.customer.screens.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.fragment_profile_info.*
import org.parceler.Parcels
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel

import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.screens.profile.editprofile.ProfileEditActivity
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment

class ProfileInfoFragment : BaseFragment() {

    companion object {
        const val DATA_USER_PROFILE = "DATA_USER_PROFILE"
    }

    private val userInfoParcel: UserInfoParcel
        get() = Parcels.unwrap<UserInfoParcel>(arguments?.getParcelable(DATA_USER_PROFILE))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_view_profile_room_name.text = String.format(
                resources.getString(R.string.profile_room),
                userInfoParcel._roomInfo?._roomNo
        )

        text_view_profile_name.text = String.format(
                resources.getString(R.string.profile_name),
                userInfoParcel._firstName,
                userInfoParcel._lastName
        )

        text_view_tel_no.text = String.format(
                resources.getString(R.string.profile_tel_no),
                userInfoParcel._mobileNo
        )

        text_view_line_id.text = String.format(
                resources.getString(R.string.profile_line_id),
                userInfoParcel._lineID
        )

        text_view_email.text = String.format(
                resources.getString(R.string.profile_email),
                userInfoParcel._email
        )

        text_view_car_parking.text = String.format(
                resources.getString(R.string.profile_car_parking),
                userInfoParcel._carRegistration
        )

        if (!userInfoParcel._imagePath.isNullOrEmpty()) {
            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(userInfoParcel._imagePath))
                    .setResizeOptions(ResizeOptions(1280, 720))
                    .build()

            val controller = Fresco.newDraweeControllerBuilder()
                    .setTapToRetryEnabled(true)
                    .setOldController(image_view_profile.controller)
                    .setImageRequest(request)
                    .build()

            image_view_profile.controller = controller
        }

        button_edit_profile.setOnClickListener {
            ProfileEditActivity.start(activity!!)
        }
    }


}
