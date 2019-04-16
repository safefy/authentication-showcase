package th.co.knightfrank.data_java.data.requests.messages

import com.squareup.moshi.Json

data class ReplyMessageDataInfoRequest(@Json(name = "ReplySubject") val replySubject: String,
                                       @Json(name = "ReplyContents") val replyContents: String,
                                       @Json(name = "ParentMessageID") val parentMessageID: Int)