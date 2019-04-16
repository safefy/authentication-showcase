package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class CreateJobRequestWrapperResponse<D>(@Json(name = "CreateJobRequestResult") val _data: D?)