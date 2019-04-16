package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.announcements.AnnouncementInfoResponse

data class InquiryDashboardResponse(@Json(name = "AnnouncesList") val _announcesList: List<AnnouncementInfoResponse>? = listOf<AnnouncementInfoResponse>())