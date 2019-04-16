package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class SaveRoomWaterMeterRequest(@Json(name = "token") val token: String?,
                                     @Json(name = "UserID") val userID: Int?,
                                     @Json(name = "BillingWaterJobID") val billingWaterJobID: Int?,
                                     @Json(name = "RoomID") val roomID: Int?,
                                     @Json(name = "MeterNo") val meterNo: String?)