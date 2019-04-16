package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterProjectRegisterResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                @Json(name = "RecordCount") val _recordCount: Int?,
                                                @Json(name = "ProjectList") val _projectList: MutableList<RegisterNewResponse>?)