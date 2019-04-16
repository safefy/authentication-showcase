package th.co.knightfrank.data_java.data.retrofit

import com.squareup.moshi.Json

data class ErrorBody(@Json(name = "errors") val errors: List<ErrorItem>)