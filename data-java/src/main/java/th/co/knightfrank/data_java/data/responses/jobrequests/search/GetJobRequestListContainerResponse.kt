package th.co.knightfrank.data_java.data.responses.jobrequests.search

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.JobRequestInfoResponse

data class GetJobRequestListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                              @Json(name = "RecordCount") val _recordCount: Int?,
                                              @Json(name = "JobRequestList") val _jobRequestList: MutableList<JobRequestInfoResponse>? = mutableListOf())