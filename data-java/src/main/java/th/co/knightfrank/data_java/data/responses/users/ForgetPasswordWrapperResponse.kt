package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class ForgetPasswordWrapperResponse<D>(@Json(name = "ForgetPasswordResult") val _data: D?)
