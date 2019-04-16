package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class SearchAnnouncementWrapperResponse<D>(@Json(name = "SearchAnnouncementResult") val _data: D?)