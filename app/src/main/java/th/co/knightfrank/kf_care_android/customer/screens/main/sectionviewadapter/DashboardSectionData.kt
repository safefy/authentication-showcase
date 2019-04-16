package th.co.knightfrank.kf_care_android.customer.screens.main.sectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardInfoResponse
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class DashboardSectionData(
        //val dashboardItemParcel: DashboardItemParcel,
        //val dashboardPinInfoResponse: DashboardPinInfoResponse,
        val dashboardInfoResponse: DashboardInfoResponse,
        val context: Context
) : SectionAdapter.SectionData