package th.co.knightfrank.data_java.data.requests.messages

import com.squareup.moshi.Json

data class CreateInboxMessageRequest(@Json(name = "UserID") val userID: Int,
                                     @Json(name = "token") val token: String,
                                     @Json(name = "MessageData") val messageData: MessageDataInfoRequest)