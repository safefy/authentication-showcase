package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json

data class DashboardInfoResponse(
        @Json(name = "CreateDisplayTime") val _createDisplayTime: String? = null,
        @Json(name = "DashboardTypeName") val _dashboardTypeName: String? = null,
        @Json(name = "DashboardTypeOrder") val _dashboardTypeOrder: Int? = null,
        @Json(name = "Detail") val _detail: String? = null,
        @Json(name = "GroupOrder") val _groupOrder: Int? = null,
        @Json(name = "ID") val _ID: Int? = null,
        @Json(name = "IsAccepted") val _isAccepted: Boolean? = null,
        @Json(name = "IsPin") val _isPin: Boolean? = false,
        @Json(name = "IsRead") val _isRead: Boolean? = false,
        @Json(name = "IsRequireAccept") val _isRequireAccept: Boolean? = false,
        @Json(name = "Title") val _title: String? = null
)
