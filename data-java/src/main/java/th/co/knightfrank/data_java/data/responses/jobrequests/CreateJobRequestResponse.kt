package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class CreateJobRequestResponse(@Json(name = "JobNo") val _jobNo: String?,
                                    @Json(name = "CreateAt") val _createAt: String?)