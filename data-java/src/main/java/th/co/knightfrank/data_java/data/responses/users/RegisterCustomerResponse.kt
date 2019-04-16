package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class RegisterCustomerResponse(@Json(name = "UserToken") val _userToken: String? = null,
                                    @Json(name = "UserRoleID") val _userRoleID: Int? = null)