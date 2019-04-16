package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class GetParcelListByUserIDWrapperResponse<D>(@Json(name = "GetParcelListByUserIDResult") val _data: D?)