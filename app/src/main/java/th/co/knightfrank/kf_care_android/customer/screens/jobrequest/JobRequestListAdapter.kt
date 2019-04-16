package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.listsectionviewadapter.JobRequestListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.listsectionviewadapter.JobRequestListViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class JobRequestListAdapter(listener: (jobRequestID: Int) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(JobRequestListSectionData::class.java, JobRequestListViewAdapter(listener, context))
    }
}