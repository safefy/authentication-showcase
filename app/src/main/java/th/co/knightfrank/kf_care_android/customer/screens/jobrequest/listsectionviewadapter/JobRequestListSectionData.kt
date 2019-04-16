package th.co.knightfrank.kf_care_android.customer.screens.jobrequest.listsectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.responses.jobrequests.JobRequestInfoResponse
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class JobRequestListSectionData(
        val jobRequestInfoResponse: JobRequestInfoResponse,
        val context: Context
) : SectionAdapter.SectionData