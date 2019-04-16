package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class CoOwnerTypeInfoResponse(
        @Json(name = "CoOwnerTypeID") val _coOwnerTypeID: Int? = null,
        @Json(name = "CoOwnerTypeName") val _CoOwnerTypeName: String? = null
)