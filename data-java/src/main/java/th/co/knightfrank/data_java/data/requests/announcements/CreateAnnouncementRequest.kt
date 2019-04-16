package th.co.knightfrank.data_java.data.requests.announcements

import com.squareup.moshi.Json

data class CreateAnnouncementRequest(@Json(name = "UserID") val userID: Int?,
                                     @Json(name = "token") val token: String?,
                                     @Json(name = "announceData") val announceData: AnnounceDataInfoRequest?)