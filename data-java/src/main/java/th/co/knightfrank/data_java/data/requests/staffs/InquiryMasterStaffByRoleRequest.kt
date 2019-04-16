package th.co.knightfrank.data_java.data.requests.staffs

import com.squareup.moshi.Json

data class InquiryMasterStaffByRoleRequest(@Json(name = "token") val token: String,
                                           @Json(name = "RoleID") val roleID: List<Int>,
                                           @Json(name = "ProjectID") val projectID: Int)