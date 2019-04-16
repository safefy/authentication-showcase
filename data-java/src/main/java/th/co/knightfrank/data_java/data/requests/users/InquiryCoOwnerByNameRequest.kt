package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class InquiryCoOwnerByNameRequest(@Json(name = "token") val token: String,
                                       @Json(name = "UserID") val userID: Int,
                                       @Json(name = "CoOwnerName") val coOwnerName: String)
