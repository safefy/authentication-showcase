package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.FloorInfoParcel
import th.co.knightfrank.data_java.data.responses.floors.FloorInfoResponse

class FloorInfoResponseMapper : Mapper<FloorInfoResponse?, FloorInfoParcel?> {
    override fun transform(source: FloorInfoResponse?): FloorInfoParcel = FloorInfoParcel(
            _activeStatus = source?._activeStatus,
            _buildingID = source?._buildingID,
            _floorID = source?._floorID,
            _floorName = source?._floorName,
            _projectID = source?._projectID
    )
}