package th.co.knightfrank.data_java.data.responses

import com.squareup.moshi.Json

data class ResponseWrapper<D>(@Json(name = "LoginResult") val _data: D?)