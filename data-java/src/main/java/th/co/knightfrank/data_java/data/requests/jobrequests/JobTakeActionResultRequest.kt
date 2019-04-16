package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class JobTakeActionResultRequest(@Json(name = "UserID") val userID: Int,
                                      @Json(name = "token") val token: String,
                                      @Json(name = "jobRepairData") val jobRepairDataInfoRequest: JobRepairDataInfoRequest)