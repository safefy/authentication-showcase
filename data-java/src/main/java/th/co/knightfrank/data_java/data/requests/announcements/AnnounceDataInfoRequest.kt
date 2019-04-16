package th.co.knightfrank.data_java.data.requests.announcements

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class AnnounceDataInfoRequest(@Json(name = "AnnounceID") val announceID: Int?,
                                   @Json(name = "StartDate") val startDate: String?,
                                   @Json(name = "EndDate") val endDate: String?,
                                   @Json(name = "IsPublic") val isPublic: Boolean?,
                                   @Json(name = "IsPin") val isPin: Boolean?,
                                   @Json(name = "IsRequireAccept") val isRequireAccept: Boolean?,
                                   @Json(name = "Location") val location: String?,
                                   @Json(name = "Title") val title: String?,
                                   @Json(name = "Detail") val detail: String?,
                                   @Json(name = "AcknowledgeUserID") val acknowledgeUserID: List<Int>?,
                                   @Json(name = "ImageFileList") val imageFileList: List<ImageFileInfoRequest>?)