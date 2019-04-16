package th.co.knightfrank.data_java.data.responses.rooms

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.HeaderResponse

data class GetMasterRoomInfoContainerResponse(@Json(name = "ResponseHeader") val _responseHeader: HeaderResponse?,
                                              @Json(name = "ResponseBody") val _responseBody: RoomsInfoResponse?)