package th.co.knightfrank.data_java.data.responses.rooms

import com.squareup.moshi.Json

data class InquiryMasterRoomListByRoomNameWrapperResponse<D>(@Json(name = "InquiryMasterRoomListByRoomNameResult") val _data: D?)