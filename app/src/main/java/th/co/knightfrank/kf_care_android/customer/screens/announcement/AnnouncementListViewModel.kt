package th.co.knightfrank.kf_care_android.customer.screens.announcement

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.announcements.SearchAnnouncementRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.announcements.AnnouncementInfoResponse
import th.co.knightfrank.domain.repositories.announcements.AnnouncementRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class AnnouncementListViewModel
@Inject
constructor(private val announcementRepository: AnnouncementRepository)
    : DisposableViewModel(), StatusViewModel<AnnouncementListViewModel.Status, AnnouncementListViewModel.ViewDataBundle> {

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun searchAnnouncementRequest(searchAnnouncementRequest: SearchAnnouncementRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                announcementRepository.searchAnnouncement(searchAnnouncementRequest)
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
                                                        announcementList = opt._data!!._announcementList
                                                )
                                        )
                                    } else {
                                        mutableStatus.value = Status.SuccessWithNoData(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        recordCount = 0,
                                                        announcementList = listOf()
                                                )
                                        )
                                    }
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    recordCount = 0,
                                                    announcementList = listOf()
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    recordCount = 0,
                                                    announcementList = listOf()
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
                              val announcementList: List<AnnouncementInfoResponse>? = listOf<AnnouncementInfoResponse>())
}