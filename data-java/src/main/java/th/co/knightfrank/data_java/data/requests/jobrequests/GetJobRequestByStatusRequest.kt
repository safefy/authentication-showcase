package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestByStatusRequest(@Json(name = "token") val token: String,
                                        @Json(name = "AssignToUserID") val userID: Int,
                                        @Json(name = "JobStatus") val jobStatus: Int)