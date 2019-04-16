package th.co.knightfrank.data_java.data.requests

import com.squareup.moshi.Json

data class DeleteImageRequest(@Json(name = "UserID") val userID: Int,
                              @Json(name = "token") val token: String,
                              @Json(name = "RefID") val refID: Int,
                              @Json(name = "ImageSource") val imageSource: Int)