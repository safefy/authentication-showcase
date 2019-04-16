package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class LoginMobileWrapperResponse<D>(@Json(name = "LoginMobileResult") val _data: D?)