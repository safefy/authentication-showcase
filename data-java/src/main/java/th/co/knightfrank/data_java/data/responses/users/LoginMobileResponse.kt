package th.co.knightfrank.data_java.data.responses.users


import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.resources.HeaderResource
import th.co.knightfrank.data_java.data.resources.login_mobile.LoginMobileResource

data class LoginMobileResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResource? = null,
                               @Json(name = "ResponseBody") val _responseBody: LoginMobileResource? = null)