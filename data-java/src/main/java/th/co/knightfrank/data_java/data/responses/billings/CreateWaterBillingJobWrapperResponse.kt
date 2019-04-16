package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class CreateWaterBillingJobWrapperResponse<D>(@Json(name = "CreateWaterBillingJobResult") val _data: D?)