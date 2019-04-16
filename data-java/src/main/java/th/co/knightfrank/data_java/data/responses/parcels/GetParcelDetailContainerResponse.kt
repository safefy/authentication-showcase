package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetParcelDetailContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                            @Json(name = "ParcelDetail") val _responseBody: ParcelInfoResponse?)