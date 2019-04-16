package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json

data class ParcelProviderInfoResponse(@Json(name = "ParcelProviderID") val _parcelProviderID: Int?,
                                      @Json(name = "ParcelProviderName") val _parcelProviderName: String?)