package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class InquiryMasterJobStatusListWrapperResponse<D>(@Json(name = "InquiryMasterJobStatusListResult") val _data: D?)