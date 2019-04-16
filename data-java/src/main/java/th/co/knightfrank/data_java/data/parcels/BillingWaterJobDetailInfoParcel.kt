package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class BillingWaterJobDetailInfoParcel @ParcelConstructor constructor(
        val _billingWaterJobDetailID: Int?,
        val _billingWaterJobID: Int?,
        val _inputDate: String?,
        val _meterNo: String?,
        val _roomID: Int?,
        val _roomNo: String?,
        val _roomWaterMeterNo: String?
)