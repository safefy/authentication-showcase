package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json

data class GetJobRequestListRequest(@Json(name = "SearchFilter") val _searchFilterInfo: SearchFilterInfoRequest?,
                                    @Json(name = "token") val _token: String?)