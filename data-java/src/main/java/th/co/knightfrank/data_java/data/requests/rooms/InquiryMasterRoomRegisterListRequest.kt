package th.co.knightfrank.data_java.data.requests.rooms

import com.squareup.moshi.Json

data class InquiryMasterRoomRegisterListRequest(@Json(name = "ProjectID") val projectID: Int?,
                                                @Json(name = "BuildingID") val buildingID: Int?,
                                                @Json(name = "FloorID") val floorID: Int?)