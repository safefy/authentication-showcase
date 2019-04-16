package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json

data class RegisterNewResponse(@Json(name = "ProjectID") val _projectID: Int?,
                               @Json(name = "ProjectNameTh") val _projectName: String?)