package th.co.knightfrank.kf_care_android.customer.screens.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.mapper.UserInfoResponseMapper
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel
import th.co.knightfrank.domain.repositories.users.UserRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class RegisterInvitationCodeViewModel
@Inject
constructor(private val userRepository: UserRepository)
    : DisposableViewModel(), StatusViewModel<RegisterInvitationCodeViewModel.Status, RegisterInvitationCodeViewModel.ViewDataBundle> {

    private val userInfoResponseMapper = UserInfoResponseMapper()

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getUserByInvitationCodeRequest(invitationCode: String, firebaseToken: String) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                userRepository.getUserByInvitationCode(invitationCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            if (opt._data!!._responseHeader!!._responseCode == "000") {
                                mutableStatus.value = Status.Success(
                                        currentValue.viewDataBundle.copy(
                                                status_msg = opt._data!!._responseHeader!!._responseDesc,
                                                userInfoParcel = userInfoResponseMapper.transform(
                                                        opt._data!!._responseBody!!.copy(
                                                                _firebaseToken = firebaseToken
                                                        )),
                                                userInvitationCode = invitationCode
                                        )
                                )
                            } else {
                                mutableStatus.value = Status.Error(
                                        opt._data!!._responseHeader!!._responseDesc!!,
                                        currentValue.viewDataBundle.copy(status_msg = opt._data!!._responseHeader!!._responseDesc)
                                )
                            }
                        }, {
                            mutableStatus.value = Status.Error("Something went wrong", ViewDataBundle())
                        })
        )
    }

    sealed class Status(viewDataBundle: ViewDataBundle) : StatusViewModel.Status<ViewDataBundle>(viewDataBundle) {
        class Stale(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Loading(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Success(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val status_msg: String? = "",
                              val userInfoParcel: UserInfoParcel? = null,
                              val userInvitationCode: String? = null)
}