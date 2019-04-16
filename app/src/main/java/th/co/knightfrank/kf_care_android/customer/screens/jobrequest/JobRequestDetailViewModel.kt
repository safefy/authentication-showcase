package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.jobrequests.*
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.JobRequestInfoResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.JobRequestTransactionInfoResponse
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.repositories.jobrequests.JobRequestRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject


class JobRequestDetailViewModel
@Inject
constructor(private val jobRequestRepository: JobRequestRepository,
            private val appSettings: AppSettings)
    : DisposableViewModel(), StatusViewModel<JobRequestDetailViewModel.Status, JobRequestDetailViewModel.ViewDataBundle> {

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getJobRequestDetailRequest(jobRequestID: Int) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.getJobRequestDetail(
                        GetJobRequestDetailRequest(
                                token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                jobRequestID = jobRequestID
                        )
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    jobRequestDetailData = opt._data!!._jobRequestDetail,
                                                    appSettingRoleID = appSettings.getInt(AppSettingsKey.ROLE_ID),
                                                    appSettingUserID = appSettings.getInt(AppSettingsKey.USER_ID)
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "014" ->
                                    mutableStatus.value = Status.Logout("Something went wrong", ViewDataBundle())
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    jobRequestDetailData = null
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun getJobRequestTransactionListRequest(jobRequestID: Int) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.getJobRequestTransactionList(GetJobRequestTransactionListRequest(
                        token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                        jobRequestID = jobRequestID
                ))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    transactionList = opt._data!!._responseBody!!._jobRequestTransactionList
                                            )
                                    )

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "014" ->
                                    mutableStatus.value = Status.Logout("Something went wrong", ViewDataBundle())
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    transactionList = listOf()
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun jobApproveRequest(jobRequestID: Int, isApprove: Boolean, comment: String) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.jobApprove(JobApproveRequest(
                        userID = appSettings.getInt(AppSettingsKey.USER_ID),
                        token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                        jobRequestID = jobRequestID,
                        isApprove = isApprove,
                        comment = comment
                ))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.SendJobApproveSuccess(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun jobAcceptRequest(jobRequestID: Int) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.jobAccept(JobAcceptRequest(
                        userID = appSettings.getInt(AppSettingsKey.USER_ID),
                        token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                        jobRequestID = jobRequestID
                ))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.JobAcceptSuccess(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun jobApproveByManagerRequest(jobRequestID: Int, isApprove: Boolean) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.jobApproveByManager(JobApproveByManagerRequest(
                        userID = appSettings.getInt(AppSettingsKey.USER_ID),
                        token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                        jobRequestID = jobRequestID,
                        isApprove = isApprove
                ))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.JobApproveByManagerSuccess(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    sealed class Status(viewDataBundle: ViewDataBundle) : StatusViewModel.Status<ViewDataBundle>(viewDataBundle) {
        class Stale(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Loading(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Success(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SuccessWithNoData(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SendJobApproveSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Logout(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)

        class JobAcceptSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class JobApproveByManagerSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val headerResponse: HeaderResponse? = null,
                              val jobRequestDetailData: JobRequestInfoResponse? = null,
                              val transactionList: List<JobRequestTransactionInfoResponse>? = listOf(),
                              val appSettingRoleID: Int? = null,
                              val appSettingUserID: Int? = null)
}