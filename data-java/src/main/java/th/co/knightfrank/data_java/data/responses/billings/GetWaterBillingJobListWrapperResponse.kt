package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class GetWaterBillingJobListWrapperResponse<D>(@Json(name = "GetWaterBillingJobListResult") val _data: D?)
