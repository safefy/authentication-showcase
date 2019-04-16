package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterParcelProviderListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                            @Json(name = "RecordCount") val _recordCount: Int?,
                                                            @Json(name = "ParcelProviderList") val _parcelProviderList: MutableList<ParcelProviderInfoResponse>? = mutableListOf())