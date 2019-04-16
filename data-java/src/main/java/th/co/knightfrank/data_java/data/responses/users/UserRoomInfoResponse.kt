package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.buildings.BuildingInfoResponse
import th.co.knightfrank.data_java.data.responses.floors.FloorInfoResponse
import th.co.knightfrank.data_java.data.responses.projects.ProjectInfoResponse
import th.co.knightfrank.data_java.data.responses.rooms.RoomsInfoResponse

data class UserRoomInfoResponse(
        @Json(name = "BuildingInfo") val _buildingInfo: BuildingInfoResponse? = null,
        @Json(name = "CoOwnerType") val _coOwnerType: CoOwnerTypeInfoResponse? = null,
        @Json(name = "FloorID") val _floorInfo: FloorInfoResponse? = null,
        @Json(name = "ProjectInfo") val _projectInfo: ProjectInfoResponse? = null,
        @Json(name = "RoomInfo") val _roomInfo: RoomsInfoResponse? = null
)