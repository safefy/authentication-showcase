package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryMasterParcelTypeListContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                        @Json(name = "RecordCount") val _recordCount: Int?,
                                                        @Json(name = "ParcelTypeList") val _parcelTypeList: MutableList<ParcelTypeInfoResponse>? = mutableListOf())