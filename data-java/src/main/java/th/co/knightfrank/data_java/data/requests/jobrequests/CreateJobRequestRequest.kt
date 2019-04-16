package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class CreateJobRequestRequest(@Json(name = "UserID") val userID: Int,
                                   @Json(name = "token") val token: String,
                                   @Json(name = "jobData") val jobData: JobDataInfoRequest)