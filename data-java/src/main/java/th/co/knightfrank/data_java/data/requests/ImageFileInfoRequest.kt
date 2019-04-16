package th.co.knightfrank.data_java.data.requests

import com.squareup.moshi.Json

data class ImageFileInfoRequest(@Json(name = "FileName") val fileName: String,
                                @Json(name = "FileContent") val fileContent: String)