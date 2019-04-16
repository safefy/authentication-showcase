package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json

data class GetParcelListByUserIDRequest(@Json(name = "UserID") val userID: Int,
                                        @Json(name = "token") val token: String)