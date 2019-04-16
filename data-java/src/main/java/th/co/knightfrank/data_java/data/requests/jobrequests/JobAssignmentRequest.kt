package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class JobAssignmentRequest(@Json(name = "UserID") val userID: Int,
                                @Json(name = "token") val token: String,
                                @Json(name = "assignJobData") val assignJobData: AssignJobDataInfoRequest)