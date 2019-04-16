package th.co.knightfrank.data_java.data.responses.users

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.responses.buildings.BuildingInfoResponse
import th.co.knightfrank.data_java.data.responses.floors.FloorInfoResponse
import th.co.knightfrank.data_java.data.responses.projects.ProjectInfoResponse
import th.co.knightfrank.data_java.data.responses.roles.RoleInfoResponse
import th.co.knightfrank.data_java.data.responses.rooms.RoomsInfoResponse

data class UserInfoResponse(
        @Json(name = "BuildingInfo") val _buildingInfo: BuildingInfoResponse? = null,
        @Json(name = "CarRegistration") val _carRegistration: String? = null,
        //@Json(name = "CreateAt") val _createAt: LocalDateTime? = null,
        @Json(name = "Email") val _email: String? = null,
        @Json(name = "FirebaseToken") val _firebaseToken: String? = null,
        @Json(name = "FirstName") val _firstName: String? = null,
        @Json(name = "FloorInfo") val _floorInfo: FloorInfoResponse? = null,
        @Json(name = "ImagePath") val _imagePath: String? = null,
        @Json(name = "IsContactLine") val _isContactLine: Boolean? = null,
        @Json(name = "IsContactMobile") val _isContactMobile: Boolean? = null,
        //@Json(name = "LastLoginTime") val _lastLoginTime: LocalDateTime? = null,
        @Json(name = "LastName") val _lastName: String? = null,
        @Json(name = "LineID") val _lineID: String? = null,
        @Json(name = "MobileNo") val _mobileNo: String? = null,
        @Json(name = "ParkingNo") val _parkingNo: String? = null,
        @Json(name = "ProjectInfo") val _projectInfo: ProjectInfoResponse? = null,
        @Json(name = "RegisterType") val _registerType: Int? = null,
        @Json(name = "RoleInfo") val _roleInfo: RoleInfoResponse? = null,
        @Json(name = "RoomInfo") val _roomInfo: RoomsInfoResponse? = null,
        //@Json(name = "UpdateAt") val _updateAt: LocalDateTime? = null,
        @Json(name = "UserID") val _userID: Int? = null,
        @Json(name = "UserRoomList") val _userRoomList: MutableList<UserRoomInfoResponse>? = null,
        @Json(name = "Username") val _username: String? = null
)