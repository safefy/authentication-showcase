package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobRequestTransactionListResponse(@Json(name = "JobRequestTransactionList") val _jobRequestTransactionList: List<JobRequestTransactionInfoResponse>? = listOf())