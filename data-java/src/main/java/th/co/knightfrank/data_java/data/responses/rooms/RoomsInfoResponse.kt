package th.co.knightfrank.data_java.data.responses.rooms

import com.squareup.moshi.Json

data class RoomsInfoResponse(
        @Json(name = "ActiveStatus") val _activeStatus: Boolean? = true,
        @Json(name = "BuildingID") val _buildingID: Int? = null,
        @Json(name = "FloorID") val _floorID: Int? = null,
        @Json(name = "ProjectID") val _projectID: Int? = null,
        @Json(name = "RoomID") val _roomID: Int? = null,
        @Json(name = "RoomNo") val _roomNo: String? = null
)