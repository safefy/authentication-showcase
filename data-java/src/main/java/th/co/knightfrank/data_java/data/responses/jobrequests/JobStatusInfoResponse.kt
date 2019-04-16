package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobStatusInfoResponse(@Json(name = "JobStatusID") val _jobStatusID: Int?,
                                 @Json(name = "JobStatusName") val _jobStatusName: String?,
                                 @Json(name = "JobStatusNameThai") val _jobStatusNameThai: String?)