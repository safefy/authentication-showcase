package th.co.knightfrank.kf_care_android.customer.screens.announcement

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.announcement.listsectionviewadapter.AnnouncementListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.announcement.listsectionviewadapter.AnnouncementListViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class AnnouncementListAdapter(listener: (announceID: Int) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(AnnouncementListSectionData::class.java, AnnouncementListViewAdapter(listener, context))
    }
}