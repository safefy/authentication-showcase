package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class ReplyInboxMessageContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)