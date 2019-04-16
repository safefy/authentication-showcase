package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobPriorityInfoResponse(@Json(name = "JobPriorityDesc") val _jobPriorityDesc: String?,
                                   @Json(name = "JobPriorityID") val _jobPriorityID: Int?,
                                   @Json(name = "JobPriorityName") val _jobPriorityName: String?)