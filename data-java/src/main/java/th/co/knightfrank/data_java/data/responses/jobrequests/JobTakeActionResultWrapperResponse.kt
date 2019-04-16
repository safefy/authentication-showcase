package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobTakeActionResultWrapperResponse<D>(@Json(name = "JobTakeActionResultResult") val _data: D?)