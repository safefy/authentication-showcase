package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class RoomInfoParcel @ParcelConstructor constructor(
        val _activeStatus: Boolean?,
        val _buildingID: Int?,
        val _floorID: Int?,
        val _projectID: Int?,
        val _roomID: Int?,
        val _roomNo: String?
)
