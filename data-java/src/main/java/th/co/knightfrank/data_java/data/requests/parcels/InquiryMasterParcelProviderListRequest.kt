package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json

data class InquiryMasterParcelProviderListRequest(@Json(name = "token") val token: String)