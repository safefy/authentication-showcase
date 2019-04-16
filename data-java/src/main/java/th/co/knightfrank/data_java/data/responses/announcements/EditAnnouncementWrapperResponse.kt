package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class EditAnnouncementWrapperResponse<D>(@Json(name = "EditAnnouncementResult") val _data: D?)