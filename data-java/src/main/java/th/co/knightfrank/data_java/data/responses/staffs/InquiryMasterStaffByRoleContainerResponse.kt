package th.co.knightfrank.data_java.data.responses.staffs

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.users.UserInfoResponse

data class InquiryMasterStaffByRoleContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                     @Json(name = "RecordCount") val _recordCount: Int?,
                                                     @Json(name = "StaffList") val _staffList: MutableList<UserInfoResponse>?)