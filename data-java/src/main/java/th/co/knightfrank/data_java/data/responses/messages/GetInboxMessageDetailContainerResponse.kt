package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetInboxMessageDetailContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                  @Json(name = "InboxMessageDetail") val _responseBody: InboxMessageInfoResponse?)