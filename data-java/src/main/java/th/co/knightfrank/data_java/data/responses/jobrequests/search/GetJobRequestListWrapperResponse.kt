package th.co.knightfrank.data_java.data.responses.jobrequests.search

import com.squareup.moshi.Json

data class GetJobRequestListWrapperResponse<D>(@Json(name = "GetJobRequestListResult") val _data: D?)