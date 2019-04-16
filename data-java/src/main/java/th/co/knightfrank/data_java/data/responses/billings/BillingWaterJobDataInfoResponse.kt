package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime

data class BillingWaterJobDataInfoResponse(@Json(name = "BillingMonthName") val _billingMonthName: String?,
                                           @Json(name = "BillingMonthNo") val _billingMonthNo: Int?,
                                           @Json(name = "BillingPeriod") val _billingPeriod: Int?,
                                           @Json(name = "BillingYear") val _billingYear: Int?,
                                           @Json(name = "BillingWaterJobID") val _billingWaterJobID: Int?,
                                           @Json(name = "CreateAt") val _createAt: LocalDateTime?,
                                           @Json(name = "CreateAtDisplay") val _createAtDisplay: String?,
                                           @Json(name = "ProjectID") val _projectID: Int?,
                                           @Json(name = "RoomQty") val _roomQty: Int?,
                                           @Json(name = "RoomTotal") val _roomTotal: Int?)