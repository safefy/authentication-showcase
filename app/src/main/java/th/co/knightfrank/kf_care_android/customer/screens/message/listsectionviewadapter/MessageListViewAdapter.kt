package th.co.knightfrank.kf_care_android.customer.screens.message.listsectionviewadapter


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

class MessageListViewAdapter(private val listener: (inboxMessageID: Int) -> Unit, private val context: Context) : SectionAdapter.SectionViewAdapter<MessageListSectionData, MessageListViewAdapter.ViewHolder> {

    override fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_dashboard, parent, false))
    }

    override fun bindViewHolder(viewHolder: ViewHolder, item: MessageListSectionData) {
        val viewModel = item
        val inboxMessageData = viewModel.inboxMessageInfoParcel

        viewHolder.setTitle(inboxMessageData._subject)
        viewHolder.setDetail(inboxMessageData._contents)
        viewHolder.setReplyStatus(inboxMessageData._isReply, context)
        viewHolder.setCreateDate(inboxMessageData._displayCreateAt)
        viewHolder.setMessageLayout(false, context)

        viewHolder.setOnClickListener {
            listener.invoke(inboxMessageData._inboxMessageID!!)
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

        fun setReplyStatus(status: Boolean?, context: Context) {
            when (status) {
                true -> text_view_is_accepted.text = context.resources.getString(R.string.message_item_replied)
                false -> text_view_is_accepted.text = context.resources.getString(R.string.message_item_waiting_reply)
            }
        }

        fun setOnClickListener(listener: () -> Unit) {
            itemView.setOnClickListener {
                listener.invoke()
            }
        }

        fun setMessageLayout(isRead: Boolean?, context: Context) {
            setMessageIsRead(isRead)
            setMessageGroup(context)
        }

        //Parcel Layout Settings
        private fun setMessageIsRead(isRead: Boolean?) {
            when (isRead) {
                true -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_message_green_bg_white)
                }
                false -> {
                    image_view_icon.setBackgroundResource(R.drawable.ic_message_white_bg_green)
                }
            }
        }

        private fun setMessageGroup(context: Context) {
            text_view_dashboard_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.green))))
            text_view_dashboard_detail.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_created_date.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            text_view_is_accepted.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.black))))
            card_view_list_item_dashboard.setCardBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white))))
        }
    }
}
