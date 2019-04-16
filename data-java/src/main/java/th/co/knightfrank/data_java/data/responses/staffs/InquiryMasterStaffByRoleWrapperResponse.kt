package th.co.knightfrank.data_java.data.responses.staffs

import com.squareup.moshi.Json

data class InquiryMasterStaffByRoleWrapperResponse<D>(@Json(name = "InquiryMasterStaffByRoleResult") val _data: D?)