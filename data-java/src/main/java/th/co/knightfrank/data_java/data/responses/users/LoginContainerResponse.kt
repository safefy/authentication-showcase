package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class LoginContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                  @Json(name = "ResponseBody") val _responseBody: LoginResponse?)
