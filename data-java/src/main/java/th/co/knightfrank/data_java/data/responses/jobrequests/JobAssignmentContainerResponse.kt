package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class JobAssignmentContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?)