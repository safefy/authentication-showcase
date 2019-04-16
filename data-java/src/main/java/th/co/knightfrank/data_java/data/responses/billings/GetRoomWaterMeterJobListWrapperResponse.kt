package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class GetRoomWaterMeterJobListWrapperResponse<D>(@Json(name = "GetRoomWaterMeterJobListResult") val _data: D?)