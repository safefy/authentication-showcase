package th.co.knightfrank.data_java.data.requests.rooms

import com.squareup.moshi.Json

data class GetMasterRoomInfoRequest(@Json(name = "RoomID") val roomID: Int,
                                    @Json(name = "token") val token: String)