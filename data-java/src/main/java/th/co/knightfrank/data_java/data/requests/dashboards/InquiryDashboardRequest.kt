package th.co.knightfrank.data_java.data.requests.dashboards

import com.squareup.moshi.Json

data class InquiryDashboardRequest(@Json(name = "UserID") val userID: String,
                                   @Json(name = "token") val token: String)