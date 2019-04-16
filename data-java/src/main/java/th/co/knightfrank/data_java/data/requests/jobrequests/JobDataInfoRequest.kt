package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class JobDataInfoRequest(@Json(name = "PriorityID") val priorityID: Int?,
                              @Json(name = "JobAreaID") val jobAreaID: Int?,
                              @Json(name = "JobSystemTypeID") val jobSystemTypeID: Int?,
                              @Json(name = "AdminAppointDate") val adminAppointDate: String?,
                              @Json(name = "AppointDate") val appointDate: String?,
                              @Json(name = "ContactName") val contactName: String,
                              @Json(name = "ContactMobileNo") val contactMobileNo: String,
                              @Json(name = "ContactRoomName") val contactRoomName: String,
                              @Json(name = "Title") val title: String,
                              @Json(name = "Detail") val detail: String,
                              @Json(name = "ImageFileList") val imageFileList: List<ImageFileInfoRequest>,
                              @Json(name = "AssignToUserID") val assignToUserID: Int?)