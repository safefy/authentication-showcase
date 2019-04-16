package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestByUserIDRequest(@Json(name = "token") val token: String,
                                        @Json(name = "UserID") val userID: Int,
                                        @Json(name = "StatusListID") val jobStatus: List<Int>)