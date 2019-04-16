package th.co.knightfrank.kf_care_android.customer.screens.jobrequest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.jobrequests.GetJobRequestByUserIDRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.JobRequestInfoResponse
import th.co.knightfrank.domain.repositories.jobrequests.JobRequestRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class JobRequestListViewModel
@Inject
constructor(private val jobRequestRepository: JobRequestRepository)
    : DisposableViewModel(), StatusViewModel<JobRequestListViewModel.Status, JobRequestListViewModel.ViewDataBundle> {

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getJobRequestByUserIDRequest(getJobRequestByUserIDRequest: GetJobRequestByUserIDRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                jobRequestRepository.getJobRequestByUserID(getJobRequestByUserIDRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    if (opt._data!!._recordCount!! > 0) {
                                        mutableStatus.value = Status.Success(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        recordCount = opt._data!!._recordCount,
                                                        jobRequestList = opt._data!!._jobRequestList
                                                )
                                        )
                                    } else {
                                        mutableStatus.value = Status.SuccessWithNoData(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        recordCount = 0,
                                                        jobRequestList = listOf()
                                                )
                                        )
                                    }
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    recordCount = 0,
                                                    jobRequestList = listOf()
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    recordCount = 0,
                                                    jobRequestList = listOf()
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
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Logout(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val headerResponse: HeaderResponse? = null,
                              val recordCount: Int? = null,
                              val jobRequestList: List<JobRequestInfoResponse>? = listOf<JobRequestInfoResponse>())
}