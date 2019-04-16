package th.co.knightfrank.data_java.data.responses.announcements

import com.squareup.moshi.Json

data class AcknowledUserInfoResponse(
        @Json(name = "AnnounceAcknowledgeID") val _announceAcknowledgeID: Int? = null
)
