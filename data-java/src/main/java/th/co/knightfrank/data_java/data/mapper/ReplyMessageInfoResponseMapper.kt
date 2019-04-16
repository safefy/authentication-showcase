package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.ReplyMessageInfoParcel
import th.co.knightfrank.data_java.data.responses.messages.ReplyMessageInfoResponse

class ReplyMessageInfoResponseMapper : Mapper<ReplyMessageInfoResponse?, ReplyMessageInfoParcel?> {

    override fun transform(source: ReplyMessageInfoResponse?): ReplyMessageInfoParcel = ReplyMessageInfoParcel(
            _contactMobile = source?._contactMobile,
            _contactName = source?._contactName,
            _contactRoomName = source?._contactRoomName,
            _contents = source?._contents,
            _createAt = source?._createAt,
            _displayCreateAt = source?._displayCreateAt,
            _fromProjectID = source?._fromProjectID,
            _fromUserID = source?._fromUserID,
            _inboxMessageID = source?._inboxMessageID,
            _inboxMessageImageList = source?._inboxMessageImageList,
            _isRead = source?._isRead,
            _isReply = source?._isReply,
            _replyMessage = if (source?._replyMessage != null) {
                this.transform(source._replyMessage)
            } else {
                null
            },
            _parentMessageID = source?._parentMessageID,
            _subject = source?._subject,
            _toProjectID = source?._toProjectID,
            _toUserID = source?._toUserID
    )
}