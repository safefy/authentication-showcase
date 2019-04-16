package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class UserRoomInfoParcel @ParcelConstructor constructor(
        val _buildingInfo: BuildingInfoParcel?,
        val _coOwnerType: CoOwnerTypeInfoParcel?,
        val _floorInfo: FloorInfoParcel?,
        val _projectInfo: ProjectInfoParcel?,
        val _roomInfo: RoomInfoParcel?
)
