package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobApproveWrapperResponse<D>(@Json(name = "JobApproveResult") val _data: D?)