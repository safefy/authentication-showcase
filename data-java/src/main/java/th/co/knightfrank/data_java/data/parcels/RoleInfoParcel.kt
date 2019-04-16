package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class RoleInfoParcel @ParcelConstructor constructor(
        val _roleID: Int?,
        val _roleName: String?
)
