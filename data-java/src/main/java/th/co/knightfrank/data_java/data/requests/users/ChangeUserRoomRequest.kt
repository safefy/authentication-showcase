package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class ChangeUserRoomRequest(@Json(name = "token") val token: String,
                                 @Json(name = "UserID") val userID: Int,
                                 @Json(name = "RoomID") val roomID: Int)