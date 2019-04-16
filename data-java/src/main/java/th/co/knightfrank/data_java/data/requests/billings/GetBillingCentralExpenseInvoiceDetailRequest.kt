package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class GetBillingCentralExpenseInvoiceDetailRequest(@Json(name = "token") val _token: String?,
                                                        @Json(name = "UserID") val _userID: Int?,
                                                        @Json(name = "BillingCentralExpenseInvoiceID") val _billingCentralExpenseInvoiceID: Int?)