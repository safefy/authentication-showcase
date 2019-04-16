package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class GetUserByInvitationCodeRequest(@Json(name = "InvitationCode") val invitationCode: String)