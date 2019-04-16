package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class FloorInfoParcel @ParcelConstructor constructor(
        val _activeStatus: Boolean?,
        val _buildingID: Int?,
        val _floorID: Int?,
        val _floorName: String?,
        val _projectID: Int?
)
