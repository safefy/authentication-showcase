package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class CreateJobRequestByAdminWrapperResponse<D>(@Json(name = "CreateJobRequestByAdminResult") val _data: D?)