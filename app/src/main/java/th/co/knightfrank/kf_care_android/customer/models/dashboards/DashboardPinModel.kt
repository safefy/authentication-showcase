package th.co.knightfrank.kf_care_android.customer.models.dashboards

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class DashboardPinModel @ParcelConstructor constructor(
        val _pinText: String?,
        val _pinCount: Int?,
        val _pinUnitText: String?,
        val _pinType: String?,
        val _detail: String?,
        val _bgColor: String?
)
