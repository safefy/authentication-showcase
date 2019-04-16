package th.co.knightfrank.kf_care_android.customer.screens.parcel.listsectionviewadapter


import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class ParcelListViewAdapter(private val listener: (parcelID: Int) -> Unit, private val context: Context) : SectionAdapter.SectionViewAdapter<ParcelListSectionData, ParcelListViewAdapter.ViewHolder> {

    override fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_dashboard, parent, false))
    }

    override fun bindViewHolder(viewHolder: ViewHolder, item: ParcelListSectionData) {
        val viewModel = item
        val jobRequestData = viewModel.parcelInfoParcel

        //viewHolder.setTitle(jobRequestData._parcelNote)
        //viewHolder.setDetail(jobRequestData._parcelProviderName, context)
        viewHolder.setTitle(jobRequestData._parcelNo)
        viewHolder.setDetail(jobRequestData._parcelNote, context)
        viewHolder.setParcelStatus(jobRequestData._isReceive, context)
        viewHolder.setCreateDate(jobRequestData._displayRegisterDate)
        viewHolder.setParcelLayout(false, context)

        viewHolder.setOnClickListener {
            listener.invoke(jobRequestData._parcelID!!)
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

        fun setDetail(detail: String?, context: Context) {
            text_view_dashboard_detail.text = detail
//            text_view_dashboard_detail.text = String.format(
//                    context.resources.getString(R.string.parcel_shipped_by),
//                    detail
//            )
        }

        fun setCreateDate(createDateString: String?) {
            text_view_created_date.text = createDateString
        }

        fun setParcelStatus(status: Boolean?, context: Context) {
            when (status) {
                true -> text_view_is_accepted.text = context.resources.getString(R.string.parcel_detail_status_received)
                false -> text_view_is_accepted.text = context.resources.getString(R.string.parcel_detail_status_waiting_receive)
            }
        }

        fun setOnClickListener(listener: () -> Unit) {
            itemView.setOnClickListener {
                listener.invoke()
            }
        }

        fun setParcelLayout(isRead: Boolean?, context: Context) {
            setParcelIsRead(isRead)
            setParcelGroup(context)
        }

        //Parcel Layout Settings
        private fun setParcelIsRead(isRead: Boolean?) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_parcel_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_parcel_bg_orange)
                }
            }
        }

        private fun setParcelGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.orange))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }
    }

}