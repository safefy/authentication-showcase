package th.co.knightfrank.kf_care_android.customer.screens.parcel.guide


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_parcel_guide.*
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment

class ParcelGuideFragment : BaseFragment() {

    companion object {
        const val DATA_PARCEL_GUIDE_POSITION = "DATA_PARCEL_GUIDE_POSITION"
    }

    private val intentParcelGuidePosition: Int
        get() = arguments?.getInt(DATA_PARCEL_GUIDE_POSITION, 0)!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_parcel_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (intentParcelGuidePosition) {
            0 -> {
                image_view.visibility = View.VISIBLE
                text_view_parcel_no.visibility = View.GONE
                image_view.setImageResource(R.drawable.ic_parcel_mailbox_white)
                text_view_parcel_information_label.text = resources.getText(R.string.parcel_guide_1_information_label)
                text_view_parcel_information_detail.text = resources.getText(R.string.parcel_guide_1_information_detail)
            }
            1 -> {
                image_view.visibility = View.GONE
                text_view_parcel_no.visibility = View.VISIBLE
                text_view_parcel_information_label.text = resources.getText(R.string.parcel_guide_2_information_label)
                text_view_parcel_information_detail.text = resources.getText(R.string.parcel_guide_2_information_detail)
            }
            2 -> {
                image_view.visibility = View.VISIBLE
                text_view_parcel_no.visibility = View.GONE
                image_view.setImageResource(R.drawable.ic_parcel_box_white)
                text_view_parcel_information_label.text = resources.getText(R.string.parcel_guide_3_information_label)
                text_view_parcel_information_detail.text = resources.getText(R.string.parcel_guide_3_information_detail)
            }
        }
    }

}
