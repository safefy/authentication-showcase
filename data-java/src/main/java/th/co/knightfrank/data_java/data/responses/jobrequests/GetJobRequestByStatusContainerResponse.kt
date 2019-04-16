package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetJobRequestByStatusContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                  @Json(name = "RecordCount") val _recordCount: Int?,
                                                  @Json(name = "JobRequestList") val _jobRequestList: List<JobRequestInfoResponse>)