package th.co.knightfrank.data_java.data.requests.buildings

import com.squareup.moshi.Json

data class InquiryMasterBuildingListRequest(@Json(name = "ProjectID") val projectID: Int?,
                                            @Json(name = "token") val token: String?)