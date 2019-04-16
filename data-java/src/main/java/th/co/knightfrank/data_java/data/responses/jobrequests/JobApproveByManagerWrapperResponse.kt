package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobApproveByManagerWrapperResponse<D>(@Json(name = "JobApproveByManagerResult") val _data: D?)