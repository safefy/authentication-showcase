package th.co.knightfrank.data_java.data.responses.projects

import com.squareup.moshi.Json

data class ProjectInfoResponse(
        @Json(name = "ActiveStatus") val _activeStatus: Boolean? = true,
        @Json(name = "ProjectAddress") val _projectAddress: String? = null,
        @Json(name = "ProjectID") val _projectID: Int? = null,
        @Json(name = "ProjectNameTh") val _projectNameTh: String? = null,
        @Json(name = "ProjectNameEn") val _projectNameEn: String? = null,
        @Json(name = "ProjectCode") val _projectCode: String? = null
)