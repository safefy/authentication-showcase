package th.co.knightfrank.kf_care_android.customer.models.parcels


import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.threeten.bp.LocalDateTime

@Parcel(Parcel.Serialization.BEAN)
data class ParcelInfoModel @ParcelConstructor constructor(
        val _checkoutDate: LocalDateTime?,
        val _checkoutName: String?,
        val _displayCheckoutDate: String?,
        val _displayRegisterDate: String?,
        val _imageFileList: MutableList<String>?,
        val _parcelID: Int?,
        val _parcelNo: String?,
        val _parcelNote: String?,
        val _parcelProviderName: String?,
        val _receiveName: String?,
        val _registerDate: LocalDateTime?,
        val _roomName: String?,
        val _senderName: String?,
        val _isReceive: Boolean? = false,
        val _isReceiveDisplayText: String?
)
