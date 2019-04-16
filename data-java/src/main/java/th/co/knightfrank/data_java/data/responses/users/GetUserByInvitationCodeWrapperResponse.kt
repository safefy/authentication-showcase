package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class GetUserByInvitationCodeWrapperResponse<D>(@Json(name = "GetUserByInvitationCodeResult") val _data: D?)