package th.co.knightfrank.data_java.data.resources

import com.squareup.moshi.Json

data class HeaderResource
constructor(@Json(name = "ResponseCode") val _responseCode: String? = null,
            @Json(name = "ResponseDesc") val _responseDesc: String? = null)
