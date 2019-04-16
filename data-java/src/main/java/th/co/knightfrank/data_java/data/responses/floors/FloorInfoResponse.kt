package th.co.knightfrank.data_java.data.responses.floors

import com.squareup.moshi.Json

data class FloorInfoResponse(
        @Json(name = "ActiveStatus") val _activeStatus: Boolean? = true,
        @Json(name = "BuildingID") val _buildingID: Int? = null,
        @Json(name = "FloorID") val _floorID: Int? = null,
        @Json(name = "FloorName") val _floorName: String? = null,
        @Json(name = "ProjectID") val _projectID: Int? = null
)
