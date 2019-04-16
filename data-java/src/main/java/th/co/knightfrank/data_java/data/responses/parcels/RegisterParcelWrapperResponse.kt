package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class RegisterParcelWrapperResponse<D>(@Json(name = "RegisterParcelResult") val _data: D?)