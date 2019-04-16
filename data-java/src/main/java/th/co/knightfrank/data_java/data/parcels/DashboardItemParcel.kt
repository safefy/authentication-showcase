package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.threeten.bp.LocalDateTime

@Parcel(Parcel.Serialization.BEAN)
data class DashboardItemParcel @ParcelConstructor constructor(
        val _announceID: Int?,
        //val _createAt: String?,
        val _createDateTime: LocalDateTime?,
        val _createDisplayTime: String?,
        val _detail: String?,
        val _endDate: String?,
        val _imageFileList: List<String>?,
        val _isAccepted: Boolean?,
        val _isPin: Boolean?,
        val _isRead: Boolean?,
        val _isRequireAccept: Boolean?,
        val _location: String?,
        val _startDate: String?,
        val _title: String?
        //val _updateAt: String?
)
