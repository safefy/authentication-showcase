package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class GetRoomWaterMeterJobListRequest(@Json(name = "token") val token: String?,
                                           @Json(name = "UserID") val userID: Int?,
                                           @Json(name = "jobData") val jobData: BillingWaterJobDataInfoRequest?)