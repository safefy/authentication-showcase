package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class AssignJobDataInfoRequest(@Json(name = "JobRequestID") val jobRequestID: Int,
                                    @Json(name = "JobPriority") val jobPriority: Int,
                                    @Json(name = "JobAreaID") val jobAreaID: Int,
                                    @Json(name = "Title") val title: String,
                                    @Json(name = "Detail") val detail: String,
                                    @Json(name = "AdminAppointDate") val adminAppointDate: String,
                                    @Json(name = "AdminComment") val adminComment: String,
                                    @Json(name = "SystemTypeID") val systemTypeID: Int,
                                    @Json(name = "AssignByUserID") val assignByUserID: Int,
                                    @Json(name = "AssignToUserID") val assignToUserID: Int)