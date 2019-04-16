package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class ChangeUserRoomWrapperResponse<D>(@Json(name = "ChangeUserRoomResult") val _data: D?)