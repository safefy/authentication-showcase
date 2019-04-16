package th.co.knightfrank.data_java.data.responses.messages


import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime

data class ReplyMessageInfoResponse(@Json(name = "ContactMobile") val _contactMobile: String?,
                                    @Json(name = "ContactName") val _contactName: String?,
                                    @Json(name = "ContactRoomName") val _contactRoomName: String?,
                                    @Json(name = "Contents") val _contents: String?,
                                    @Json(name = "CreateAt") val _createAt: LocalDateTime? = null,
                                    @Json(name = "DisplayCreateAt") val _displayCreateAt: String?,
                                    @Json(name = "FromProjectID") val _fromProjectID: Int?,
                                    @Json(name = "FromUserID") val _fromUserID: Int?,
                                    @Json(name = "InboxMessageID") val _inboxMessageID: Int?,
                                    @Json(name = "InboxMessageImageList") val _inboxMessageImageList: List<String>?,
                                    @Json(name = "IsRead") val _isRead: Boolean? = null,
                                    @Json(name = "IsReply") val _isReply: Boolean? = null,
                                    @Json(name = "ParentMessageID") val _parentMessageID: Int?,
                                    @Json(name = "ReplyMessage") val _replyMessage: ReplyMessageInfoResponse?,
                                    @Json(name = "Subject") val _subject: String?,
                                    @Json(name = "ToProjectID") val _toProjectID: Int?,
                                    @Json(name = "ToUserID") val _toUserID: Int?)