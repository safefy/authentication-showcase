package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime
import th.co.knightfrank.data_java.data.responses.rooms.RoomsInfoResponse

data class BillingWaterJobInvoiceDetailInfoResponse(@Json(name = "BillingWaterJobInvoiceDetailID") val _billingWaterJobInvoiceDetailID: Int?,
                                                    @Json(name = "ProjectID") val _projectID: Int?,
                                                    @Json(name = "BillingWaterJobDetailID") val _billingWaterJobDetailID: Int?,
                                                    @Json(name = "BillingInfo") val _billingInfo: BillingWaterJobDataInfoResponse?,
                                                    @Json(name = "RoomID") val _roomID: Int?,
                                                    @Json(name = "RoomInfo") val _roomInfo: RoomsInfoResponse?,
                                                    @Json(name = "InvoiceNo") val _invoiceNo: String?,
                                                    @Json(name = "InvoiceDate") val _invoiceDate: LocalDateTime?,
                                                    @Json(name = "PaymentDueDate") val _paymentDueDate: LocalDateTime?,
                                                    @Json(name = "LastMonthInputDate") val _lastMonthInputDate: LocalDateTime?,
                                                    @Json(name = "LastMonthMeterNo") val _lastMonthMeterNo: Double?,
                                                    @Json(name = "ThisMonthInputDate") val _thisMonthInputDate: LocalDateTime?,
                                                    @Json(name = "ThisMonthMeterNo") val _thisMonthMeterNo: Double?,
                                                    @Json(name = "PricePerUnit") val _pricePerUnit: Double?,
                                                    @Json(name = "IncludeVatPercent") val _includeVatPercent: Double?,
                                                    @Json(name = "NetTotalPrice") val _netTotalPrice: Double?,
                                                    @Json(name = "InvoiceUserID") val _invoiceUserID: Int?,
                                                    @Json(name = "PaymentStatus") val _paymentStatus: Int?,
                                                    @Json(name = "PaymentUserID") val _paymentUserID: Int?,
                                                    @Json(name = "PaymentDate") val _paymentDate: LocalDateTime?,
                                                    @Json(name = "PaymentImageFileName") val _paymentImageFileName: String?,
                                                    @Json(name = "ReceiveNo") val _receiveNo: String?,
                                                    @Json(name = "ReceiveDate") val _receiveDate: LocalDateTime?,
                                                    @Json(name = "ReceiveUserID") val _receiveUserID: Int?)