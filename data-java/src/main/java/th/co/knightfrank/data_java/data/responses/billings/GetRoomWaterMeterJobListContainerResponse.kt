package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetRoomWaterMeterJobListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                     @Json(name = "RecordCount") val _recordCount: Int?,
                                                     @Json(name = "ResponseBody") val _billingWaterJobDetailList: MutableList<BillingWaterJobDetailDataInfoResponse>? = mutableListOf())