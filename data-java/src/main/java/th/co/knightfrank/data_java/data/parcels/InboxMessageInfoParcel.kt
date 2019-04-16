package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.threeten.bp.LocalDateTime

@Parcel(Parcel.Serialization.BEAN)
data class InboxMessageInfoParcel @ParcelConstructor constructor(
        val _contactMobile: String?,
        val _contactName: String?,
        val _contactRoomName: String?,
        val _contents: String?,
        val _createAt: LocalDateTime? = null,
        val _displayCreateAt: String?,
        val _fromProjectID: Int?,
        val _fromUserID: Int?,
        val _inboxMessageID: Int?,
        val _inboxMessageImageList: List<String>?,
        val _isRead: Boolean? = null,
        val _isReply: Boolean? = null,
        val _replyMessage: ReplyMessageInfoParcel? = null,
        val _parentMessageID: Int?,
        val _subject: String?,
        val _toProjectID: Int?,
        val _toUserID: Int?
)