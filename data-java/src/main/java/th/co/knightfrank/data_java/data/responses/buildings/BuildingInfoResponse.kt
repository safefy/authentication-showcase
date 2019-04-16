package th.co.knightfrank.data_java.data.responses.buildings

import com.squareup.moshi.Json

data class BuildingInfoResponse(
        @Json(name = "ActiveStatus") val _activeStatus: Boolean? = true,
        @Json(name = "BuildNameTh") val _buildingNameTh: String? = null,
        @Json(name = "BuildNameEn") val _buildingNameEn: String? = null,
        @Json(name = "BuildingID") val _buildingID: Int? = null,
        @Json(name = "ProjectID") val _projectID: Int? = null
)
