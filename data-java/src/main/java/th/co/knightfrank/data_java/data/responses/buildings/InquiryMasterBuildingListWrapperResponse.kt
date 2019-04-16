package th.co.knightfrank.data_java.data.responses.buildings

import com.squareup.moshi.Json

data class InquiryMasterBuildingListWrapperResponse<D>(@Json(name = "InquiryMasterBuildingListResult") val _data: D?)