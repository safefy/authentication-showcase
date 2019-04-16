package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class BillingDataInfoRequest(@Json(name = "BillingMonth") val billingMonth: Int?,
                                  @Json(name = "BillingYear") val billingYear: Int?,
                                  @Json(name = "BillingPeriod") val billingPeriod: Int?)