package th.co.knightfrank.data_java.data.requests

import com.squareup.moshi.Json

data class SearchFilterInfoRequest(@Json(name = "Title") val title: String? = null,
                                   @Json(name = "Detail") val detail: String? = null,
                                   @Json(name = "Location") val location: String? = null,
                                   @Json(name = "DateFrom") val dateFrom: String? = null,
                                   @Json(name = "DateTo") val dateTo: String? = null)