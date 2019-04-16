package th.co.knightfrank.data_java.data.responses

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.resources.HeaderResource

data class ResponseEntity<D>(@Json(name = "ResponseHeader") val _responseHeader: HeaderResource?,
                             @Json(name = "ResponseBody") val _data: D?)