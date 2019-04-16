package th.co.knightfrank.data_java.data.responses.rooms

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterRoomRegisterListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                          @Json(name = "RecordCount") val _recordCount: Int?,
                                                          @Json(name = "RoomList") val _roomInfoList: MutableList<RoomsInfoResponse>? = mutableListOf())