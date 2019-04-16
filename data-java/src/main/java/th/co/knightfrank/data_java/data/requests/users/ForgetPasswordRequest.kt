package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class ForgetPasswordRequest(@Json(name = "Email") val _email: String)