package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class CreateAnnouncementWrapperResponse<D>(@Json(name = "CreateAnnouncementResult") val _data: D?)