package th.co.knightfrank.data_java.data.requests.floors

import com.squareup.moshi.Json

data class InquiryMasterFloorRegisterListRequest(@Json(name = "ProjectID") val projectID: Int?,
                                                 @Json(name = "BuildingID") val buildingID: Int?)