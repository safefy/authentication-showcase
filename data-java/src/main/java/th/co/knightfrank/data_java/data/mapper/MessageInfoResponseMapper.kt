package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.InboxMessageInfoParcel
import th.co.knightfrank.data_java.data.responses.messages.InboxMessageInfoResponse

class MessageInfoResponseMapper : Mapper<InboxMessageInfoResponse?, InboxMessageInfoParcel?> {
    private val replyMessageInfoResponseMapper = ReplyMessageInfoResponseMapper()

    override fun transform(source: InboxMessageInfoResponse?): InboxMessageInfoParcel = InboxMessageInfoParcel(
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
            _replyMessage = replyMessageInfoResponseMapper.transform(source?._replyMessage),
            _parentMessageID = source?._parentMessageID,
            _subject = source?._subject,
            _toProjectID = source?._toProjectID,
            _toUserID = source?._toUserID
    )
}