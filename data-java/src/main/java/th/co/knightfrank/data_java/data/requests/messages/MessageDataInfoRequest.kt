package th.co.knightfrank.data_java.data.requests.messages

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class MessageDataInfoRequest(
        @Json(name = "Subject") val subject: String,
        @Json(name = "Contents") val contents: String,
        @Json(name = "ContactName") val contactName: String,
        @Json(name = "ContactMobileNo") val contactMobileNo: String,
        @Json(name = "ContactRoomName") val contactRoomName: String,
        @Json(name = "ToProjectID") val toProjectID: Int?,
        @Json(name = "ToUserID") val toUserID: Int?,
        @Json(name = "ImageFileList") val imageFileList: List<ImageFileInfoRequest>)