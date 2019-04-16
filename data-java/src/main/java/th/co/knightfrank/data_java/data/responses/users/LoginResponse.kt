package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class LoginResponse(@Json(name = "LoginToken") val _loginToken: String? = null,
                         @Json(name = "UserInfo") val _userInfo: UserInfoResponse? = null)