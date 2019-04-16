package th.co.knightfrank.data_java.data.parcels

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.threeten.bp.LocalDateTime

@Parcel(Parcel.Serialization.BEAN)
data class BillingWaterJobInfoParcel @ParcelConstructor constructor(
        val _billingMonthName: String?,
        val _billingMonthNo: Int?,
        val _billingPeriod: Int?,
        val _billingYear: Int?,
        val _billingWaterJobID: Int?,
        val _createAt: LocalDateTime?,
        val _createAtDisplay: String?,
        val _projectID: Int?,
        val _roomQty: Int?,
        val _roomTotal: Int?
)
