package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class InquiryCoOwnerByNameWrapperResponse<D>(@Json(name = "InquiryCoOwnerByNameResult") val _data: D?)