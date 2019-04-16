package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class SearchAnnouncementContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                               @Json(name = "RecordCount") val _recordCount: Int?,
                                               @Json(name = "AnnounctList") val _announcementList: List<AnnouncementInfoResponse>)