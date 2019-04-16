package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobAssignmentWrapperResponse<D>(@Json(name = "JobAssignmentResult") val _data: D?)