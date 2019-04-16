package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class ResetPasswordWrapperResponse<D>(@Json(name = "ResetPasswordResult") val _data: D?)