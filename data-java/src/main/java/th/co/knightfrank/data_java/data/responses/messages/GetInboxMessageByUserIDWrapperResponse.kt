package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json

data class GetInboxMessageByUserIDWrapperResponse<D>(@Json(name = "GetInboxMessageByUserIDResult") val _data: D?)