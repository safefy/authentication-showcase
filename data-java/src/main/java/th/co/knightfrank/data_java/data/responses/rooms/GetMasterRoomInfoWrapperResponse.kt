package th.co.knightfrank.data_java.data.responses.rooms

import com.squareup.moshi.Json

data class GetMasterRoomInfoWrapperResponse<D>(@Json(name = "GetMasterRoomInfoResult") val _data: D?)