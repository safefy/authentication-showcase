package th.co.knightfrank.data_java.data.responses.parcels

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class CheckoutParcelContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)