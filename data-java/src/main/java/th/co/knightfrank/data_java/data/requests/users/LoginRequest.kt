package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class LoginRequest(@Json(name = "Username") val username: String,
                        @Json(name = "Psswd") val password: String)