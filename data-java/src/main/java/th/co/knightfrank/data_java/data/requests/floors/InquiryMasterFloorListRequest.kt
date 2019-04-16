package th.co.knightfrank.data_java.data.requests.floors

import com.squareup.moshi.Json

data class InquiryMasterFloorListRequest(@Json(name = "ProjectID") val projectID: Int?,
                                         @Json(name = "BuildingID") val buildingID: Int?,
                                         @Json(name = "token") val token: String?)