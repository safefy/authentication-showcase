package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class InquiryCoOwnerByNameContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                                 @Json(name = "RecordCount") val _recordCount: Int?,
                                                 @Json(name = "CoOwnerList") val _coOwnerList: MutableList<UserInfoResponse>? = null)