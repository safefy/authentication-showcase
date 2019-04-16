package th.co.knightfrank.kf_care_android.customer.screens.parcel.listsectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.parcels.ParcelInfoParcel
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class ParcelListSectionData(
        val parcelInfoParcel: ParcelInfoParcel,
        val context: Context
) : SectionAdapter.SectionData