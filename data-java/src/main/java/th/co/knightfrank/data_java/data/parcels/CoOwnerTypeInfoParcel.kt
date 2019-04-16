package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class CoOwnerTypeInfoParcel @ParcelConstructor constructor(
        val _coOwnerTypeID: Int?,
        val _coOwnerTypeName: String?
)
