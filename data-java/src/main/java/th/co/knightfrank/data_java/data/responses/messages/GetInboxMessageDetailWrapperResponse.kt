package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json

data class GetInboxMessageDetailWrapperResponse<D>(@Json(name = "GetInboxMessageDetailResult") val _data: D?)