package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterJobAreaListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                     @Json(name = "RecordCount") val _recordCount: Int?,
                                                     @Json(name = "JobAreaList") val _jobAreaList: MutableList<JobAreaInfoResponse>?)