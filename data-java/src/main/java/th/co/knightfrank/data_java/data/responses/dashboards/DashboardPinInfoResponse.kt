package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json

data class DashboardPinInfoResponse(
        @Json(name = "PinText") val _pinText: String? = null,
        @Json(name = "PinCount") val _pinCount: Int? = null,
        @Json(name = "PinUnitText") val _pinUnitText: String? = null,
        @Json(name = "PinType") val _pinType: String? = null,
        @Json(name = "Detail") val _detail: String? = null,
        @Json(name = "BgColor") val _bgColor: String? = null
)
