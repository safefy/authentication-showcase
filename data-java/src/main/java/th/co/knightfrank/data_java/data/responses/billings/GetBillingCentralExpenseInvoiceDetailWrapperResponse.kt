package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json

data class GetBillingCentralExpenseInvoiceDetailWrapperResponse<D>(@Json(name = "GetBillingCentralExpenseInvoiceDetailResult") val _data: D?)