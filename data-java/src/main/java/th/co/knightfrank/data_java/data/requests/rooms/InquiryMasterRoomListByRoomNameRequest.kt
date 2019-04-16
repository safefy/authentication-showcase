package th.co.knightfrank.data_java.data.requests.rooms

import com.squareup.moshi.Json

data class InquiryMasterRoomListByRoomNameRequest(@Json(name = "ProjectID") val _projectID: Int,
                                                  @Json(name = "token") val _token: String,
                                                  @Json(name = "RoomName") val _roomName: String)