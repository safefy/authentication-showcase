package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterJobPriorityListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                         @Json(name = "RecordCount") val _recordCount: Int?,
                                                         @Json(name = "JobPriorityList") val _jobPriorityList: MutableList<JobPriorityInfoResponse>?)