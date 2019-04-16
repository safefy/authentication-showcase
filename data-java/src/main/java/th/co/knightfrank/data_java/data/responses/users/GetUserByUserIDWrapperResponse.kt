package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class GetUserByUserIDWrapperResponse<D>(@Json(name = "GetUserByUserIDResult") val _data: D?)