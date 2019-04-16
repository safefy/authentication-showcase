package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobSystemTypeInfoResponse(@Json(name = "JobSystemTypeDesc") val _jobSystemTypeDesc: String?,
                                     @Json(name = "JobSystemTypeID") val _jobSystemTypeID: Int?,
                                     @Json(name = "JobSystemTypeName") val _jobSystemTypeName: String?)