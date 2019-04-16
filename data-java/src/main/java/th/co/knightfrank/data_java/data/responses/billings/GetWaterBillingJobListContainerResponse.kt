package th.co.knightfrank.data_java.data.responses.billings

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetWaterBillingJobListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                   @Json(name = "RecordCount") val _recordCount: Int?,
                                                   @Json(name = "ResponseBody") val _billingWaterJobList: MutableList<BillingWaterJobDataInfoResponse>? = mutableListOf())