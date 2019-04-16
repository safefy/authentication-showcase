package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class ResetPasswordRequest(@Json(name = "token") val _token: String,
                                @Json(name = "UserID") val _userID: Int,
                                @Json(name = "NewPsswd") val _newPassword: String)