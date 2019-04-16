package th.co.knightfrank.data_java.data.requests.announcements

import com.squareup.moshi.Json

data class GetAnnouncementDetailRequest(@Json(name = "token") val token: String,
                                        @Json(name = "AnnounceID") val announceID: Int)