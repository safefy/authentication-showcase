package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json

data class RegisterParcelRequest(@Json(name = "token") val token: String,
                                 @Json(name = "ParcelData") val parcelDataInfo: ParcelDataInfoRequest)