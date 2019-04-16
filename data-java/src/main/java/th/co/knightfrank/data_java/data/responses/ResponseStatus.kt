package th.co.knightfrank.data_java.data.responses

import com.squareup.moshi.Json

data class ResponseStatus
constructor(@Json(name = "ResponseCode") val _responseCode: String? = null,
            @Json(name = "ResponseDesc") val _responseDesc: String? = null)
