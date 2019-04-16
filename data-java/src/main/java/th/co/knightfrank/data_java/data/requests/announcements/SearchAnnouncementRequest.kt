package th.co.knightfrank.data_java.data.requests.announcements

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.SearchFilterInfoRequest

data class SearchAnnouncementRequest(@Json(name = "searchFilter") val searchFilter: SearchFilterInfoRequest? = null,
                                     @Json(name = "token") val token: String)