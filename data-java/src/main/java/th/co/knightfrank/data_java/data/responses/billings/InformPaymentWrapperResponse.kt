package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class InformPaymentWrapperResponse<D>(@Json(name = "InformPaymentResult") val _data: D?)