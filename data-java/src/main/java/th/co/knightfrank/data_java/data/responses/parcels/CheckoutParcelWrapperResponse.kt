package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class CheckoutParcelWrapperResponse<D>(@Json(name = "CheckoutParcelResult") val _data: D?)