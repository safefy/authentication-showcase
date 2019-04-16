package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestByUserIDWrapperResponse<D>(@Json(name = "GetJobRequestByUserIDResult") val _data: D?)