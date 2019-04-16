package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json

data class GetParcelDetailRequest(@Json(name = "UserID") val userID: Int,
                                  @Json(name = "token") val token: String,
                                  @Json(name = "ParcelID") val _parcelID: Int)