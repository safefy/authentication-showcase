package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class AcceptAnnouncementWrapperResponse<D>(@Json(name = "AcceptAnnouncementResult") val _data: D?)