package th.co.knightfrank.kf_care_android.customer.screens.parcel

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.parcel.listsectionviewadapter.ParcelListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.parcel.listsectionviewadapter.ParcelListViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class ParcelListAdapter(listener: (parcelID: Int) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(ParcelListSectionData::class.java, ParcelListViewAdapter(listener, context))
    }
}