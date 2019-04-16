package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.AnnouncementInfoParcel
import th.co.knightfrank.data_java.data.responses.announcements.AnnouncementInfoResponse

class AnnouncementInfoResponseMapper : Mapper<AnnouncementInfoResponse, AnnouncementInfoParcel> {
    override fun transform(source: AnnouncementInfoResponse): AnnouncementInfoParcel = AnnouncementInfoParcel(
            _announceID = source._announceID,
            //_createAt = source._createAt,
            _createDateTime = source._createDateTime,
            _createDisplayTime = source._createDisplayTime,
            _detail = source._detail,
            _endDate = source._endDate,
            _endDateDisplay = source._endDateDisplay,
            _imageFileList = source._imageFileList,
            _isAccepted = source._isAccepted,
            _isPin = source._isPin,
            _isRead = source._isRead,
            _isRequireAccept = source._isRequireAccept,
            _location = source._location,
            _startDate = source._startDate,
            _startDateDisplay = source._startDateDisplay,
            _title = source._title,
            _isPublic = source._isPublic,
            _acknowledgeUserIDList = source._acknowledgeUserList.let {
                val list: MutableList<Int>? = mutableListOf()
                it?.forEach {
                    list?.add(it._announceAcknowledgeID!!)
                }
                return@let list
            }
            //_updateAt = source._updateAt
    )
}