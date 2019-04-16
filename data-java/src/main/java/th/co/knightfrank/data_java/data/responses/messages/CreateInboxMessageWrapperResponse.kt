package th.co.knightfrank.data_java.data.responses.messages

import com.squareup.moshi.Json

data class CreateInboxMessageWrapperResponse<D>(@Json(name = "CreateInboxMessageResult") val _data: D?)