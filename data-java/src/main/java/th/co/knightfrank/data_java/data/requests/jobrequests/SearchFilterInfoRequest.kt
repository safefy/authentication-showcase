package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class SearchFilterInfoRequest(@Json(name = "JobNo") val _jobNo: String?,
                                   @Json(name = "JobAreaID") val _jobAreaID: MutableList<Int>? = mutableListOf(),
                                   @Json(name = "PriorityID") val _priorityID: MutableList<Int>? = mutableListOf(),
                                   @Json(name = "JobStatusID") val _jobStatusID: MutableList<Int>? = mutableListOf(),
                                   @Json(name = "CreateDateFrom") val _createDateFrom: String?,
                                   @Json(name = "CreateDateTo") val _createDateTo: String?,
                                   @Json(name = "Title") val _title: String?,
                                   @Json(name = "Detail") val _detail: String?,
                                   @Json(name = "ContactName") val _contactName: String?,
                                   @Json(name = "ContactMobileNo") val _contactMobileNo: String?,
                                   @Json(name = "ContactRoomNo") val _contactRoomNo: String?,
                                   @Json(name = "SystemTypeName") val _systemTypeName: String?,
                                   @Json(name = "JobAssignToStaffName") val _jobAssignToStaffName: String?)