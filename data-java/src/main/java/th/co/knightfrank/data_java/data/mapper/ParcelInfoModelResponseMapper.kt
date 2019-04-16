package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.ParcelInfoParcel
import th.co.knightfrank.data_java.data.responses.parcels.ParcelInfoResponse

class ParcelInfoModelResponseMapper : Mapper<ParcelInfoResponse?, ParcelInfoParcel?> {
    override fun transform(source: ParcelInfoResponse?): ParcelInfoParcel = ParcelInfoParcel(
            _checkoutDate = source?._checkoutDate,
            _checkoutName = source?._checkoutName,
            _displayCheckoutDate = source?._displayCheckoutDate,
            _displayRegisterDate = source?._displayRegisterDate,
            _imageFileList = source?._imageFileList,
            _parcelID = source?._parcelID,
            _parcelNo = source?._parcelNo,
            _parcelNote = source?._parcelNote,
            _parcelProviderName = source?._parcelProviderName,
            _parcelTypeName = source?._parcelTypeName,
            _receiveName = source?._receiveName,
            _registerDate = source?._registerDate,
            _roomName = source?._roomName,
            _senderName = source?._senderName,
            _isReceive = !source?._checkoutName.isNullOrEmpty()
    )
}