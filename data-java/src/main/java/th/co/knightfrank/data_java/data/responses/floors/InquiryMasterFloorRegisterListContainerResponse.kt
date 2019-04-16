package th.co.knightfrank.data_java.data.responses.floors

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterFloorRegisterListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                           @Json(name = "RecordCount") val _recordCount: Int?,
                                                           @Json(name = "FloorList") val _floorInfoList: MutableList<FloorInfoResponse>? = mutableListOf())