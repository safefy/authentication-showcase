package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class BuildingInfoParcel @ParcelConstructor constructor(
        val _activeStatus: Boolean?,
        val _buildingNameTh: String?,
        val _buildingNameEn: String?,
        val _buildingID: Int?,
        val _projectID: Int?
)
