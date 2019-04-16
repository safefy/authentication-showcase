package th.co.knightfrank.kf_care_android.customer.screens.announcement

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.mapper.AnnouncementInfoResponseMapper
import th.co.knightfrank.data_java.data.parcels.AnnouncementInfoParcel
import th.co.knightfrank.data_java.data.requests.announcements.AcceptAnnouncementRequest
import th.co.knightfrank.data_java.data.requests.announcements.GetAnnouncementDetailRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.domain.repositories.announcements.AnnouncementRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class AnnouncementDetailViewModel
@Inject
constructor(private val announcementRepository: AnnouncementRepository)
    : DisposableViewModel(), StatusViewModel<AnnouncementDetailViewModel.Status, AnnouncementDetailViewModel.ViewDataBundle> {

    private val announcementInfoResponseMapper = AnnouncementInfoResponseMapper()

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getAnnouncementDetailRequest(announcementDetailRequest: GetAnnouncementDetailRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                announcementRepository.getAnnouncementDetail(announcementDetailRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = announcementInfoResponseMapper.transform(opt._data!!._responseBody!!)
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = null
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = null
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun acceptAnnouncementRequest(acceptAnnouncementRequest: AcceptAnnouncementRequest, announcementInfoParcel: AnnouncementInfoParcel) {

        //Log.e("acceptAnnouncementRequest", "AnnounceID : " + announcementInfoParcel._announceID)

        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                announcementRepository.acceptAnnouncement(acceptAnnouncementRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = announcementInfoParcel.copy(_isAccepted = acceptAnnouncementRequest.isAccept)
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = currentValue.viewDataBundle.announcementInfoParcel
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    announcementInfoParcel = announcementInfoParcel
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong, Internet not connected", ViewDataBundle())
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
                              val announcementInfoParcel: AnnouncementInfoParcel? = null)
}