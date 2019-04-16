package th.co.knightfrank.data_java.data.responses.floors

import com.squareup.moshi.Json

data class InquiryMasterFloorListWrapperResponse<D>(@Json(name = "InquiryMasterFloorListResult") val _data: D?)