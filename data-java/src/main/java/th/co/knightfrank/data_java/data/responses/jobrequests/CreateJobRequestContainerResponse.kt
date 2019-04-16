package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class CreateJobRequestContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                             @Json(name = "ResponseBody") val _responseBody: CreateJobRequestResponse?)