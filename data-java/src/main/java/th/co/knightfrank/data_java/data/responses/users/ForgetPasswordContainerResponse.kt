package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class ForgetPasswordContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)