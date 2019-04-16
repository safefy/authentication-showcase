package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class JobApproveRequest(@Json(name = "UserID") val userID: Int,
                             @Json(name = "token") val token: String,
                             @Json(name = "JobRequestID") val jobRequestID: Int,
                             @Json(name = "IsApprove") val isApprove: Boolean,
                             @Json(name = "Comments") val comment: String)