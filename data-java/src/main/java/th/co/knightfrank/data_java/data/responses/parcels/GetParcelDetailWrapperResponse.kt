package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class GetParcelDetailWrapperResponse<D>(@Json(name = "GetParcelDetailResult") val _data: D?)