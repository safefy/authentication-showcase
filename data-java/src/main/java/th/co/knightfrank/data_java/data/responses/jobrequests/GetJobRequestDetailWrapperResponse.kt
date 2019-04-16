package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestDetailWrapperResponse<D>(@Json(name = "GetJobRequestDetailResult") val _data: D?)