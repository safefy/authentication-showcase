package th.co.knightfrank.kf_care_android.customer.screens.main

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter.DashboardSectionData
import th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter.DashboardViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class MainAdapter(listener: (dashboardID: Int, dashboardTypeName: String) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(DashboardSectionData::class.java, DashboardViewAdapter(listener, context))
    }
}