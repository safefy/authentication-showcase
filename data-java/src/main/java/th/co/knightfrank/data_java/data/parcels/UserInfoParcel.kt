package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class UserInfoParcel @ParcelConstructor constructor(
        val _buildingInfo: BuildingInfoParcel?,
        val _carRegistration: String?,
        //@Json(name = "CreateAt") val _createAt: LocalDateTime,
        val _email: String?,
        val _firebaseToken: String?,
        val _firstName: String?,
        val _floorInfo: FloorInfoParcel?,
        val _imagePath: String?,
        val _isContactLine: Boolean?,
        val _isContactMobile: Boolean?,
        //@Json(name = "LastLoginTime") val _lastLoginTime: LocalDateTime,
        val _lastName: String?,
        val _lineID: String?,
        val _mobileNo: String?,
        val _parkingNo: String?,
        val _projectInfo: ProjectInfoParcel?,
        val _registerType: Int?,
        val _roleInfo: RoleInfoParcel?,
        val _roomInfo: RoomInfoParcel?,
        //@Json(name = "UpdateAt") val _updateAt: LocalDateTime,
        val _userID: Int?,
        val _userRoomList: MutableList<UserRoomInfoParcel>?,
        val _username: String?
)
