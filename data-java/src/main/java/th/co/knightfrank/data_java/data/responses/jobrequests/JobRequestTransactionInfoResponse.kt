package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json
import org.threeten.bp.LocalDateTime
import th.co.knightfrank.data_java.data.responses.users.UserInfoResponse

data class JobRequestTransactionInfoResponse(@Json(name = "CreateDateTime") val _createDateTime: LocalDateTime? = null,
                                             @Json(name = "CreateDisplayTime") val _createDisplayTime: String? = null,
                                             @Json(name = "JobRequestID") val _jobRequestID: Int? = null,
                                             @Json(name = "JobRequestTransactionsID") val _jobRequestTransactionID: Int? = null,
                                             @Json(name = "JobStatus") val _jobStatus: JobStatusInfoResponse? = null,
                                             @Json(name = "TransactionDetail") val _transactionDetail: String? = null,
                                             @Json(name = "UserInfo") val _userInfo: UserInfoResponse? = null)