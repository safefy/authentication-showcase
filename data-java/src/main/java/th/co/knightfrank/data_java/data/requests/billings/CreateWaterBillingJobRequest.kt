package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class CreateWaterBillingJobRequest(@Json(name = "UserID") val userID: Int?,
                                        @Json(name = "token") val token: String?,
                                        @Json(name = "BillingData") val billingData: BillingDataInfoRequest?)