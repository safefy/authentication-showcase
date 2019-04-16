package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.UserRoomInfoParcel
import th.co.knightfrank.data_java.data.responses.users.UserRoomInfoResponse

class UserRoomInfoResponseMapper : Mapper<UserRoomInfoResponse?, UserRoomInfoParcel?> {

    private val roomInfoResponseMapper = RoomInfoResponseMapper()
    private val projectInfoResponseMapper = ProjectInfoResponseMapper()
    private val buildingInfoResponseMapper = BuildingInfoResponseMapper()
    private val floorInfoResponseMapper = FloorInfoResponseMapper()
    private val coOwnerInfoResponseMapper = CoOwnerInfoResponseMapper()

    override fun transform(source: UserRoomInfoResponse?): UserRoomInfoParcel = UserRoomInfoParcel(
            _buildingInfo = buildingInfoResponseMapper.transform(source?._buildingInfo),
            _coOwnerType = coOwnerInfoResponseMapper.transform(source?._coOwnerType),
            _floorInfo = floorInfoResponseMapper.transform(source?._floorInfo),
            _projectInfo = projectInfoResponseMapper.transform(source?._projectInfo),
            _roomInfo = roomInfoResponseMapper.transform(source?._roomInfo)
    )
}