package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class ChangeUserRoomInfoResponse(@Json(name = "LoginToken") val _loginToken: String?,
                                      @Json(name = "UserInfo") val _userInfo: UserInfoResponse?)