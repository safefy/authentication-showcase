package th.co.knightfrank.kf_care_android.customer.screens.announcement.listsectionviewadapter

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

class AnnouncementListViewAdapter(private val listener: (announceID: Int) -> Unit, private val context: Context) : SectionAdapter.SectionViewAdapter<AnnouncementListSectionData, AnnouncementListViewAdapter.ViewHolder> {

    override fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_dashboard, parent, false))
    }

    override fun bindViewHolder(viewHolder: ViewHolder, item: AnnouncementListSectionData) {
        val viewModel = item
        val announcementData = viewModel.announcementInfoResponse

        viewHolder.setTitle(announcementData._title)
        viewHolder.setDetail(announcementData._detail)
        viewHolder.setCreateDate(announcementData._createDisplayTime)
        viewHolder.setAnnounceIsRead(announcementData._isRead, announcementData._isRequireAccept, announcementData._isAccepted)
        viewHolder.setAnnounceIsAccepted(announcementData._isRequireAccept, announcementData._isAccepted, context)

        viewHolder.setOnClickListener {
            listener.invoke(announcementData._announceID!!)
        }
    }


    class ViewHolder(itemView: View) : SectionAdapter.ViewHolder(itemView) {
        private val card_view_list_item_dashboard: CardView = itemView.findViewById(R.id.card_view_list_item_dashboard)
        private val image_view_icon: ImageView = itemView.findViewById(R.id.image_view_icon)
        private val text_view_dashboard_title: TextView = itemView.findViewById(R.id.text_view_dashboard_title)
        private val text_view_dashboard_detail: TextView = itemView.findViewById(R.id.text_view_dashboard_detail)
        private val text_view_created_date: TextView = itemView.findViewById(R.id.text_view_created_date)
        private val text_view_is_accepted: TextView = itemView.findViewById(R.id.text_view_is_accepted)

        //Annoucement Layout Settings
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

        fun setAnnounceIsRead(isRead: Boolean?, isRequireAccept: Boolean?, isAccepted: Boolean?) {
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

        fun setAnnounceIsAccepted(isRequireAccept: Boolean?, isAccepted: Boolean?, context: Context?) {
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
    }

}