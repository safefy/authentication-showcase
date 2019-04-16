package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime

data class AnnouncementInfoResponse(
        @Json(name = "AnnounceID") val _announceID: Int? = null,
        //@Json(name = "CreateAt") val _createAt: String? = null,
        @Json(name = "CreateDateTime") val _createDateTime: LocalDateTime? = null,
        @Json(name = "CreateDisplayTime") val _createDisplayTime: String? = null,
        @Json(name = "Detail") val _detail: String? = null,
        @Json(name = "EndDate") val _endDate: LocalDateTime? = null,
        @Json(name = "EndDateDisplay") val _endDateDisplay: String? = null,
        @Json(name = "ImageFileList") val _imageFileList: List<String>? = listOf<String>(),
        @Json(name = "IsAccepted") val _isAccepted: Boolean? = null,
        @Json(name = "IsPin") val _isPin: Boolean? = false,
        @Json(name = "IsRead") val _isRead: Boolean? = false,
        @Json(name = "IsRequireAccept") val _isRequireAccept: Boolean? = false,
        @Json(name = "Location") val _location: String? = null,
        @Json(name = "StartDate") val _startDate: LocalDateTime? = null,
        @Json(name = "StartDateDisplay") val _startDateDisplay: String? = null,
        @Json(name = "Title") val _title: String? = null,
        @Json(name = "IsPublic") val _isPublic: Boolean? = null,
        @Json(name = "AcknowledUserList") val _acknowledgeUserList: List<AcknowledUserInfoResponse>? = listOf()
        //@Json(name = "UpdateAt") val _updateAt: String? = null
)
