package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class SaveRoomWaterMeterWrapperResponse<D>(@Json(name = "SaveRoomWaterMeterResult") val _data: D?)