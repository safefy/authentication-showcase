package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class GetWaterBillingJobListRequest(@Json(name = "token") val token: String?,
                                         @Json(name = "UserID") val userID: Int?,
                                         @Json(name = "BillingYear") val billingYear: Int?,
                                         @Json(name = "BillingMonth") val billingMonth: Int?)