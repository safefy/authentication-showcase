package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class BillingWaterJobDetailDataInfoResponse(@Json(name = "BillingWaterJobDetailID") val _billingWaterJobDetailID: Int?,
                                                 @Json(name = "BillingWaterJobID") val _billingWaterJobID: Int?,
                                                 @Json(name = "InputDate") val _inputDate: String?,
                                                 @Json(name = "MeterNo") val _meterNo: String?,
                                                 @Json(name = "RoomID") val _roomID: Int?,
                                                 @Json(name = "RoomNo") val _roomNo: String?,
                                                 @Json(name = "RoomWaterMeterNo") val _roomWaterMeterNo: String?)