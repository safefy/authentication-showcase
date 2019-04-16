package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestTransactionListWrapperResponse<D>(@Json(name = "GetJobRequestTransactionListResult") val _data: D?)