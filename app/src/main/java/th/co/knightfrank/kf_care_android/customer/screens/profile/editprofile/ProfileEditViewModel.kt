package th.co.knightfrank.kf_care_android.customer.screens.profile.editprofile


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.mapper.UserInfoResponseMapper
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel
import th.co.knightfrank.data_java.data.requests.users.GetUserByUserIDRequest
import th.co.knightfrank.data_java.data.requests.users.UpdateCustomerInfoRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.domain.repositories.users.UserRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class ProfileEditViewModel
@Inject
constructor(private val userRepository: UserRepository)
    : DisposableViewModel(), StatusViewModel<ProfileEditViewModel.Status, ProfileEditViewModel.ViewDataBundle> {

    private val userInfoResponseMapper = UserInfoResponseMapper()

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getUserByUserIDRequest(getUserByUserIDRequest: GetUserByUserIDRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                userRepository.getUserByUserID(getUserByUserIDRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = userInfoResponseMapper.transform(opt._data!!._responseBody!!)
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong, Internet not connected", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun updateCustomerInfoRequest(updateCustomerInfoRequest: UpdateCustomerInfoRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                userRepository.updateCustomerInfo(updateCustomerInfoRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.SubmitSuccess(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!)
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "015" ->
                                    mutableStatus.value = Status.ErrorException(
                                            message = opt._data!!._responseHeader!!._responseDesc!!,
                                            viewDataBundle = currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "016" ->
                                    mutableStatus.value = Status.ErrorException(
                                            message = opt._data!!._responseHeader!!._responseDesc!!,
                                            viewDataBundle = currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "017" ->
                                    mutableStatus.value = Status.ErrorException(
                                            message = opt._data!!._responseHeader!!._responseDesc!!,
                                            viewDataBundle = currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "018" ->
                                    mutableStatus.value = Status.ErrorException(
                                            message = opt._data!!._responseHeader!!._responseDesc!!,
                                            viewDataBundle = currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    userInfoParcel = null
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

        class SubmitSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val headerResponse: HeaderResponse? = null,
                              val userInfoParcel: UserInfoParcel? = null)
}