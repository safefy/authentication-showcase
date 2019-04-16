package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class GetAnnouncementDetailWrapperResponse<D>(@Json(name = "GetAnnouncementDetailResult") val _data: D?)