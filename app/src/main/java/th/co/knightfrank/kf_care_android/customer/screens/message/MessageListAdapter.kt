package th.co.knightfrank.kf_care_android.customer.screens.message

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.message.listsectionviewadapter.MessageListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.message.listsectionviewadapter.MessageListViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class MessageListAdapter(listener: (inboxMessageID: Int) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(MessageListSectionData::class.java, MessageListViewAdapter(listener, context))
    }
}