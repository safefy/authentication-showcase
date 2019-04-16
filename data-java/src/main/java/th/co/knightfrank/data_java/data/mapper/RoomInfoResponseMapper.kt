package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.RoomInfoParcel
import th.co.knightfrank.data_java.data.responses.rooms.RoomsInfoResponse

class RoomInfoResponseMapper : Mapper<RoomsInfoResponse?, RoomInfoParcel?> {
    override fun transform(source: RoomsInfoResponse?): RoomInfoParcel = RoomInfoParcel(
            _activeStatus = source?._activeStatus,
            _buildingID = source?._buildingID,
            _floorID = source?._floorID,
            _projectID = source?._projectID,
            _roomID = source?._roomID,
            _roomNo = source?._roomNo
    )
}