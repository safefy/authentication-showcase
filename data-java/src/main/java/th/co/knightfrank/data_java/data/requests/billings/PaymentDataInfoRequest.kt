package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class PaymentDataInfoRequest(@Json(name = "BillingInvoiceDetailID") val _billingInvoiceDetailID: Int?,
                                  @Json(name = "BillingType") val _billingType: Int?,
                                  @Json(name = "ImageFile") val _imageFile: ImageFileInfoRequest? = null)