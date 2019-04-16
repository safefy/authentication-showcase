package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.BuildingInfoParcel
import th.co.knightfrank.data_java.data.responses.buildings.BuildingInfoResponse

class BuildingInfoResponseMapper : Mapper<BuildingInfoResponse?, BuildingInfoParcel?> {
    override fun transform(source: BuildingInfoResponse?): BuildingInfoParcel = BuildingInfoParcel(
            _activeStatus = source?._activeStatus,
            _buildingNameTh = source?._buildingNameTh,
            _buildingNameEn = source?._buildingNameEn,
            _buildingID = source?._buildingID,
            _projectID = source?._projectID
    )
}