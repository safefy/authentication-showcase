package th.co.knightfrank.data_java.data.requests.dashboards

import com.squareup.moshi.Json

data class InquiryDashboardListRequest(@Json(name = "UserID") val UserID: Int,
                                       @Json(name = "token") val token: String)