package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class EditAnnouncementContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)