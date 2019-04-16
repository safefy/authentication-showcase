package th.co.knightfrank.kf_care_android.customer.screens.message.listsectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.parcels.InboxMessageInfoParcel
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class MessageListSectionData(
        val inboxMessageInfoParcel: InboxMessageInfoParcel,
        val context: Context
) : SectionAdapter.SectionData