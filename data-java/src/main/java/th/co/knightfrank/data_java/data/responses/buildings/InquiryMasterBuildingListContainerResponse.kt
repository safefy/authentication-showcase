package th.co.knightfrank.data_java.data.responses.buildings

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterBuildingListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                      @Json(name = "RecordCount") val _recordCount: Int?,
                                                      @Json(name = "BuildingList") val _buildingList: MutableList<BuildingInfoResponse>? = mutableListOf())