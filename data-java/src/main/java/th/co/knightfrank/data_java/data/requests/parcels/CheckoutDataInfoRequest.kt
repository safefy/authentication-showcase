package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class CheckoutDataInfoRequest(@Json(name = "ParcelID") val _parcelID: Int?,
                                   @Json(name = "CheckoutName") val _checkoutName: String?,
                                   @Json(name = "CheckoutUserID") val _checkoutUserID: Int?,
                                   @Json(name = "ImageFileList") val _imageFileList: MutableList<ImageFileInfoRequest>? = mutableListOf())