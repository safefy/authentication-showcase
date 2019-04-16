package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobAcceptWrapperResponse<D>(@Json(name = "JobAcceptResult") val _data: D?)