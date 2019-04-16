package th.co.knightfrank.data_java.data.resources.login_mobile

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.resources.users.UserInfoResource

data class LoginMobileResource
constructor(@Json(name = "LoginToken") private var _loginToken: String? = null,
            @Json(name = "UserInfo") private var _userInfo: UserInfoResource? = null) {

    val loginToken: String
        get() = _loginToken.orEmpty()

    val userInfo: UserInfoResource?
        get() = _userInfo
}