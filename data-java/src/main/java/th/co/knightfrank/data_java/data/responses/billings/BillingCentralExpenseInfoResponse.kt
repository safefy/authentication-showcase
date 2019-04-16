package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime

data class BillingCentralExpenseInfoResponse(@Json(name = "BillingCentralExpenseInvoiceDetailID") val _billingCentralExpenseInvoiceDetailID: Int?,
                                             @Json(name = "RoomID") val _roomID: Int?,
                                             @Json(name = "RoomNo") val _roomNo: String?,
                                             @Json(name = "RoomArea") val _roomArea: String?,
                                             @Json(name = "InvoiceNo") val _invoiceNo: String?,
                                             @Json(name = "InvoiceDate") val _invoiceDate: LocalDateTime?,
                                             @Json(name = "PaymentDueDate") val _paymentDueDate: LocalDateTime?,
                                             @Json(name = "PricePerSqm") val _pricePerSqm: Double?,
                                             @Json(name = "NetTotalPrice") val _netTotalPrice: Double?,
                                             @Json(name = "PaymentStatus") val _paymentStatus: Int?,
                                             @Json(name = "PaymentDate") val _paymentDate: LocalDateTime?,
                                             @Json(name = "PaymentImageFileName") val _paymentImageFileName: String?,
                                             @Json(name = "ReceiveNo") val _receiveNo: String?,
                                             @Json(name = "ReceiveDate") val _receiveDate: LocalDateTime?)
