package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetInboxMessageByUserIDContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                    @Json(name = "InboxMessageList") val _responseBody: MutableList<InboxMessageInfoResponse>?)