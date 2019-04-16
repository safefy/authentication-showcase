package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class LoginWrapperResponse<D>(@Json(name = "LoginResult") val _data: D?)