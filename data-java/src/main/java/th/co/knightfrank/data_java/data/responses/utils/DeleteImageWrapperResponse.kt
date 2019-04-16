package th.co.knightfrank.data_java.data.responses.utils

import com.squareup.moshi.Json

data class DeleteImageWrapperResponse<D>(@Json(name = "DeleteImageResult") val _data: D?)