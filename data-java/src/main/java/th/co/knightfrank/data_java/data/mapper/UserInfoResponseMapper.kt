package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.UserInfoParcel
import th.co.knightfrank.data_java.data.parcels.UserRoomInfoParcel
import th.co.knightfrank.data_java.data.responses.users.UserInfoResponse

class UserInfoResponseMapper : Mapper<UserInfoResponse?, UserInfoParcel?> {

    private val roomInfoResponseMapper = RoomInfoResponseMapper()
    private val projectInfoResponseMapper = ProjectInfoResponseMapper()
    private val buildingInfoResponseMapper = BuildingInfoResponseMapper()
    private val floorInfoResponseMapper = FloorInfoResponseMapper()
    private val roleInfoResponseMapper = RoleInfoResponseMapper()
    private val userRoomInfoResponseMapper = UserRoomInfoResponseMapper()

    override fun transform(source: UserInfoResponse?): UserInfoParcel = UserInfoParcel(
            _buildingInfo = buildingInfoResponseMapper.transform(source?._buildingInfo),
            _carRegistration = source?._carRegistration,
            //@Json(name = "CreateAt") val _createAt: LocalDateTime,
            _email = source?._email,
            _firebaseToken = source?._firebaseToken,
            _firstName = source?._firstName,
            _floorInfo = floorInfoResponseMapper.transform(source?._floorInfo),
            _imagePath = source?._imagePath,
            _isContactLine = source?._isContactLine,
            _isContactMobile = source?._isContactMobile,
            //@Json(name = "LastLoginTime") _lastLoginTime = LocalDateTime,
            _lastName = source?._lastName,
            _lineID = source?._lineID,
            _mobileNo = source?._mobileNo,
            _parkingNo = source?._parkingNo,
            _projectInfo = projectInfoResponseMapper.transform(source?._projectInfo),
            _registerType = source?._registerType,
            _roleInfo = roleInfoResponseMapper.transform(source?._roleInfo),
            _roomInfo = roomInfoResponseMapper.transform(source?._roomInfo),
            //@Json(name = "UpdateAt") _updateAt = LocalDateTime,
            _userID = source?._userID,
            _userRoomList = let {
                val list: MutableList<UserRoomInfoParcel>? = mutableListOf()
                source?._userRoomList?.forEach {
                    list?.add(userRoomInfoResponseMapper.transform(it))
                }
                return@let list
            },
            _username = source?._username
    )
}