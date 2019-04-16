package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json

data class InquiryDashboardListResponse(@Json(name = "DashboardList") val _dashboardList: List<DashboardInfoResponse>? = listOf<DashboardInfoResponse>(),
                                        @Json(name = "DashboardPin") val _dashboardPin: List<DashboardPinInfoResponse>? = listOf<DashboardPinInfoResponse>())