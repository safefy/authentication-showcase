package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetParcelListByUserIDContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                  @Json(name = "ParcelList") val _responseBody: MutableList<ParcelInfoResponse>?)