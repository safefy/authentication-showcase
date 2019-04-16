package th.co.knightfrank.kf_care_android.customer.screens.announcement.listsectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.responses.announcements.AnnouncementInfoResponse
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class AnnouncementListSectionData(
        val announcementInfoResponse: AnnouncementInfoResponse,
        val context: Context
) : SectionAdapter.SectionData