package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobAreaInfoResponse(@Json(name = "JobAreaID") val _jobAreaID: Int?,
                               @Json(name = "JobAreaName") val _jobAreaName: String?)