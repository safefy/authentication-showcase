package th.co.knightfrank.data_java.data.requests.announcements

import com.squareup.moshi.Json

data class AcceptAnnouncementRequest(@Json(name = "token") val token: String,
                                     @Json(name = "AnnounceID") val announceID: Int,
                                     @Json(name = "UserID") val userID: Int,
                                     @Json(name = "IsAccept") val isAccept: Boolean)