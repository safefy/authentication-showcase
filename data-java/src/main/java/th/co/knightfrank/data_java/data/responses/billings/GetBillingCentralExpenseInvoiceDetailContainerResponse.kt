package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetBillingCentralExpenseInvoiceDetailContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                                  @Json(name = "ResponseBody") val _responseBody: BillingCentralExpenseInfoResponse?)