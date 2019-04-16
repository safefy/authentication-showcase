package th.co.knightfrank.data_java.data.responses.dashboards

import com.squareup.moshi.Json

data class InquiryDashboardWrapperResponse<D>(@Json(name = "InquiryDashboardResult") val _data: D?)