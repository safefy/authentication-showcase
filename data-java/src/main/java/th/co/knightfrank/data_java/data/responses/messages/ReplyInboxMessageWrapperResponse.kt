package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json

data class ReplyInboxMessageWrapperResponse<D>(@Json(name = "ReplyInboxMessageResult") val _data: D?)