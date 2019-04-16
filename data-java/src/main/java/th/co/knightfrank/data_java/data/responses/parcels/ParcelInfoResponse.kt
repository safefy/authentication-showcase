package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime

data class ParcelInfoResponse(@Json(name = "CheckoutDate") val _checkoutDate: LocalDateTime? = null,
                              @Json(name = "CheckoutName") val _checkoutName: String?,
                              @Json(name = "CheckoutUserID") val _checkoutUserID: Int?,
                              @Json(name = "DisplayCheckoutDate") val _displayCheckoutDate: String?,
                              @Json(name = "DisplayRegisterDate") val _displayRegisterDate: String?,
                              @Json(name = "ImageFileList") val _imageFileList: MutableList<String>?,
                              @Json(name = "ParcelID") val _parcelID: Int?,
                              @Json(name = "ParcelNo") val _parcelNo: String?,
                              @Json(name = "ParcelNote") val _parcelNote: String?,
                              @Json(name = "ParcelProviderID") val _parcelProviderID: Int?,
                              @Json(name = "ParcelProviderName") val _parcelProviderName: String?,
                              @Json(name = "ParcelTypeID") val _parcelTypeID: Int?,
                              @Json(name = "ParcelTypeName") val _parcelTypeName: String?,
                              @Json(name = "ProjectID") val _projectID: Int?,
                              @Json(name = "ReceiveName") val _receiveName: String?,
                              @Json(name = "RegisterDate") val _registerDate: LocalDateTime?,
                              @Json(name = "RegisterUserID") val _registerUserID: Int?,
                              @Json(name = "RoomID") val _roomID: Int?,
                              @Json(name = "RoomName") val _roomName: String?,
                              @Json(name = "SenderName") val _senderName: String?)