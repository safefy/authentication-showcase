package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class UpdateCustomerInfoWrapperResponse<D>(@Json(name = "UpdateCustomerInfoResult") val _data: D?)