package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json

data class InquiryDashboardListWrapperResponse<D>(@Json(name = "InquiryDashboardListResult") val _data: D?)