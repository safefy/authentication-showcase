package th.co.knightfrank.data_java.data.requests.messages

import com.squareup.moshi.Json

data class GetInboxMessageByUserIDRequest(@Json(name = "UserID") val userID: Int,
                                          @Json(name = "token") val token: String)