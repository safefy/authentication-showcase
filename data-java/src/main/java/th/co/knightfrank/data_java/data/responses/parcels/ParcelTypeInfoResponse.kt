package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class ParcelTypeInfoResponse(@Json(name = "ParcelTypeID") val _parcelTypeID: Int?,
                                  @Json(name = "ParcelTypeName") val _parcelTypeName: String?)