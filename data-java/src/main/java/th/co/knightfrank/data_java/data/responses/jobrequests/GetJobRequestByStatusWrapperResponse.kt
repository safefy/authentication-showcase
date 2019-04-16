package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestByStatusWrapperResponse<D>(@Json(name = "GetJobRequestByStatusResult") val _data: D?)