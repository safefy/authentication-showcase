package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class ProjectInfoParcel @ParcelConstructor constructor(
        val _activeStatus: Boolean?,
        val _projectAddress: String?,
        val _projectID: Int?,
        val _projectNameTh: String?,
        val _projectNameEn: String?,
        val _projectCode: String?
)
