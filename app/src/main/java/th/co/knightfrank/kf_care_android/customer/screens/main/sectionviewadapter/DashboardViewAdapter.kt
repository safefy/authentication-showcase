package th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import th.co.knightfrank.data_java.data.entities.dashboards.DashboardTypeName
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardInfoResponse
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class DashboardViewAdapter(private val listener: (dashboardID: Int, dashboardTypeName: String) -> Unit, private val context: Context) : SectionAdapter.SectionViewAdapter<DashboardSectionData, DashboardViewAdapter.ViewHolder> {

    override fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_dashboard, parent, false))
    }

    override fun bindViewHolder(viewHolder: ViewHolder, item: DashboardSectionData) {
        val mainViewModel = item
        val dashboardItemData = mainViewModel.dashboardInfoResponse

        when (dashboardItemData._dashboardTypeName) {
            DashboardTypeName.ANNOUNCE.identifier -> viewHolder.setAnnounceLayout(dashboardItemData, context)
            DashboardTypeName.JOB_REQUEST.identifier -> viewHolder.setJobRequestLayout(dashboardItemData._isRead, context)
            DashboardTypeName.PARCEL.identifier -> viewHolder.setParcelLayout(dashboardItemData._isRead, context)
            DashboardTypeName.MESSAGE.identifier -> viewHolder.setMessageLayout(dashboardItemData._isRead, context)
            DashboardTypeName.BILLING.identifier,
            DashboardTypeName.BILLING_WATER.identifier,
            DashboardTypeName.BILLING_CENTRAL_EXPENSE.identifier -> viewHolder.setBillingLayout(dashboardItemData._isRead, context)
        }

        viewHolder.setTitle(dashboardItemData._title)
        viewHolder.setDetail(dashboardItemData._detail)
        viewHolder.setCreateDate(dashboardItemData._createDisplayTime)

        viewHolder.setOnClickListener {
            listener.invoke(dashboardItemData._ID!!, dashboardItemData._dashboardTypeName!!)
        }

    }

    class ViewHolder(itemView: View) : SectionAdapter.ViewHolder(itemView) {
        private val card_view_list_item_dashboard: CardView = itemView.findViewById(R.id.card_view_list_item_dashboard)
        private val image_view_icon: ImageView = itemView.findViewById(R.id.image_view_icon)
        private val text_view_dashboard_title: TextView = itemView.findViewById(R.id.text_view_dashboard_title)
        private val text_view_dashboard_detail: TextView = itemView.findViewById(R.id.text_view_dashboard_detail)
        private val text_view_created_date: TextView = itemView.findViewById(R.id.text_view_created_date)
        private val text_view_is_accepted: TextView = itemView.findViewById(R.id.text_view_is_accepted)

        fun setTitle(title: String?) {
            text_view_dashboard_title.text = title
        }

        fun setDetail(detail: String?) {
            text_view_dashboard_detail.text = detail
        }

        fun setCreateDate(createDateString: String?) {
            text_view_created_date.text = createDateString
        }

        fun setOnClickListener(listener: () -> Unit) {
            itemView.setOnClickListener {
                listener.invoke()
            }
        }

        //Layout Select
        fun setAnnounceLayout(dashboardItemData: DashboardInfoResponse, context: Context?) {
            setAnnounceIsRead(dashboardItemData._isRead, dashboardItemData._isRequireAccept, dashboardItemData._isAccepted)
            setAnnounceIsAccepted(dashboardItemData._isRequireAccept, dashboardItemData._isAccepted, context)
        }

        fun setJobRequestLayout(isRead: Boolean?, context: Context) {
            setJobRequestIsRead(isRead, context)
        }

        fun setParcelLayout(isRead: Boolean?, context: Context) {
            setParcelIsRead(isRead, context)
        }

        fun setMessageLayout(isRead: Boolean?, context: Context) {
            setMessageIsRead(isRead, context)
        }

        fun setBillingLayout(isRead: Boolean?, context: Context) {
            setBillingIsRead(isRead, context)
        }


        //Annoucement Layout Settings
        private fun setAnnounceIsRead(isRead: Boolean?, isRequireAccept: Boolean?, isAccepted: Boolean?) {
            if (isRequireAccept!! && isAccepted == null) {
                when (isRead) {
                    true -> {
                        image_view_icon.setBackgroundResource(R.drawable.ic_annouce_bg_red)
                    }
                    false -> {
                        image_view_icon.setBackgroundResource(R.drawable.ic_annouce_bg_white)
                    }
                }
            } else {
                when (isRead) {
                    true -> {
                        image_view_icon.setBackgroundResource(R.drawable.ic_annouce_bg_white)
                    }
                    false -> {
                        image_view_icon.setBackgroundResource(R.drawable.ic_annouce_bg_red)
                    }
                }
            }
        }

        private fun setAnnounceIsAccepted(isRequireAccept: Boolean?, isAccepted: Boolean?, context: Context?) {
            text_view_is_accepted.visibility = View.VISIBLE
            when (isRequireAccept) {
                true -> {
                    when (isAccepted) {
                        true -> {
                            text_view_is_accepted.setText(R.string.announcement_text_accepted)
                            setAnnounceNotRequireAcceptGroup(context!!)
                        }
                        false -> {
                            text_view_is_accepted.setText(R.string.announcement_text_not_accepted)
                            setAnnounceNotRequireAcceptGroup(context!!)
                        }
                        null -> {
                            text_view_is_accepted.setText(R.string.announcement_text_waiting_accepted)
                            setAnnounceHasRequireAcceptGroup(context!!)
                        }
                    }
                }
                false -> {
                    text_view_is_accepted.visibility = View.GONE
                    setAnnounceNotRequireAcceptGroup(context!!)
                }
            }
        }

        private fun setAnnounceHasRequireAcceptGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
            text_view_is_accepted.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.kf_red))))
        }

        private fun setAnnounceNotRequireAcceptGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.kf_red))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }


        //JobRequest Layout Settings
        private fun setJobRequestIsRead(isRead: Boolean?, context: Context) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_maintain_blue_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_maintain_bg_blue)
                }
            }
            setJobRequestGroup(context)
        }

        private fun setJobRequestGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.blue))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            //text_view_is_accepted.setTextColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.visibility = View.GONE
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }

        //Parcel Layout Setting
        private fun setParcelIsRead(isRead: Boolean?, context: Context) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_parcel_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_parcel_bg_orange)
                }
            }
            setParcelGroup(context)
        }

        private fun setParcelGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.orange))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            //text_view_is_accepted.setTextColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.visibility = View.GONE
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }

        //Message Layout Setting
        private fun setMessageIsRead(isRead: Boolean?, context: Context) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_message_green_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_message_white_bg_green)
                }
            }
            setMessageGroup(context)
        }

        private fun setMessageGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.green))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            //text_view_is_accepted.setTextColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.visibility = View.GONE
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }

        //Billing Layout Setting
        private fun setBillingIsRead(isRead: Boolean?, context: Context) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_bill_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_bill_bg_violet)
                }
            }
            setBillingGroup(context)
        }

        private fun setBillingGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.card_billing_bg))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            //text_view_is_accepted.setTextColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.visibility = View.GONE
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }

    }

}