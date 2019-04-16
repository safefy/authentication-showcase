package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class ParcelDataInfoRequest(@Json(name = "RoomID") val _roomID: Int?,
                                 @Json(name = "ReceiveName") val _receiveName: String?,
                                 @Json(name = "SenderName") val _senderName: String?,
                                 @Json(name = "ParcelProviderID") val _parcelProviderID: Int?,
                                 @Json(name = "ParcelTypeID") val _parcelTypeID: Int?,
                                 @Json(name = "ParcelStatus") val _parcelStatus: Int?,
                                 @Json(name = "ParcelNote") val _parcelNote: String?,
                                 @Json(name = "UserID") val _userID: Int?,
                                 @Json(name = "ImageFileList") val _imageFileList: MutableList<ImageFileInfoRequest>? = mutableListOf())