package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class RegisterNewCustomerWrapperResponse<D>(@Json(name = "RegisterNewCustomerResult") val _data: D?)