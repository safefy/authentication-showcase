package th.co.knightfrank.data_java.data.responses.utils

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class DeleteImageContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)