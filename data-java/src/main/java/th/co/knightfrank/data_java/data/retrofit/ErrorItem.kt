package th.co.knightfrank.data_java.data.retrofit

import com.squareup.moshi.Json


data class ErrorItem(@Json(name = "code") val code: String?,
                     @Json(name = "status") val status: Int,
                     @Json(name = "title") val title: String?)