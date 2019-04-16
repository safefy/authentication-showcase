package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class RegisterCustomerWrapperResponse<D>(@Json(name = "RegisterCustomerResult") val _data: D?)