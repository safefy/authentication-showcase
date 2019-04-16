package th.co.knightfrank.kf_care_android.customer.screens.main


import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_card.*
import org.parceler.Parcels
import th.co.knightfrank.data_java.data.entities.dashboards.DashboardTypeName
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.models.dashboards.DashboardPinModel
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment

class MainCardFragment : BaseFragment() {

    companion object {
        const val DATA_DASHBOARD_PIN = "DATA_DASHBOARD_PIN"
        const val DATA_INDEX = "DATA_INDEX"
    }

    private val intentDashboardPinData: DashboardPinModel
        get() = Parcels.unwrap(arguments?.getParcelable(DATA_DASHBOARD_PIN))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_view_type_num_rows.text = String.format(
                resources.getString(R.string.dashboard_card_type_message_item_num_rows),
                intentDashboardPinData._pinCount
        )

        text_view_type_detail.text = intentDashboardPinData._detail

        when (DashboardTypeName.from(intentDashboardPinData._pinType!!)) {
            DashboardTypeName.ALL -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_all_title)
                linear_layout_card_type_container.visibility = View.INVISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_annouce_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_all_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_all_bg))))
            }
            DashboardTypeName.JOB_REQUEST -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_job_request_title)
                linear_layout_card_type_container.visibility = View.VISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_maintain_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_job_request_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_job_request_bg))))
            }
            DashboardTypeName.ANNOUNCE -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_announcement_title)
                linear_layout_card_type_container.visibility = View.VISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_annouce_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_announcement_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_announcement_bg))))
            }
            DashboardTypeName.PARCEL -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_parcels_title)
                linear_layout_card_type_container.visibility = View.VISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_parcel_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_parcels_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_parcels_bg))))
            }
            DashboardTypeName.BILLING -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_billing_title)
                linear_layout_card_type_container.visibility = View.VISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_billing_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_billing_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_billing_bg))))
            }
            DashboardTypeName.MESSAGE -> {
                text_view_type_title.text = resources.getString(R.string.dashboard_card_type_message_title)
                linear_layout_card_type_container.visibility = View.VISIBLE
                image_view_card_type.setImageResource(R.drawable.ic_message_white)
                card_view_dashboard_card_item.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_message_container))))
                relative_layout_dashboard_card_item.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.card_message_bg))))
            }
        }

    }

}
