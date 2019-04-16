package th.co.knightfrank.kf_care_android.customer.screens.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.users.LoginMobileRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.repositories.users.UserRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class SplashViewModel
@Inject
constructor(private val userRepository: UserRepository,
            private val appSettings: AppSettings)
    : DisposableViewModel(), StatusViewModel<SplashViewModel.Status, SplashViewModel.ViewDataBundle> {

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun loginMobileWithEmail(loginMobileRequest: LoginMobileRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())
        addDisposable(
                userRepository.loginMobileWithEmail(loginMobileRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({

                            opt ->

                            if (opt._data!!._responseHeader!!._responseCode == "000") {
                                appSettings.setValue(AppSettingsKey.LOGIN_TOKEN,
                                        opt._data!!._responseBody!!._loginToken!!)
                                appSettings.setValue(AppSettingsKey.CAR_REGISTRATION,
                                        opt._data!!._responseBody!!._userInfo!!._carRegistration!!)
                                appSettings.setValue(AppSettingsKey.EMAIL,
                                        opt._data!!._responseBody!!._userInfo!!._email!!)
                                appSettings.setValue(AppSettingsKey.FIREBASE_TOKEN,
                                        opt._data!!._responseBody!!._userInfo!!._firebaseToken!!)
                                appSettings.setValue(AppSettingsKey.FIRST_NAME,
                                        opt._data!!._responseBody!!._userInfo!!._firstName!!)
                                appSettings.setValue(AppSettingsKey.IMAGE_PATH,
                                        opt._data!!._responseBody!!._userInfo!!._imagePath!!)
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_LINE,
                                        opt._data!!._responseBody!!._userInfo!!._isContactLine!!)
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_MOBILE,
                                        opt._data!!._responseBody!!._userInfo!!._isContactMobile!!)
                                appSettings.setValue(AppSettingsKey.LAST_NAME,
                                        opt._data!!._responseBody!!._userInfo!!._lastName!!)
                                appSettings.setValue(AppSettingsKey.LINE_ID,
                                        opt._data!!._responseBody!!._userInfo!!._lineID!!)
                                appSettings.setValue(AppSettingsKey.MOBILE_NO,
                                        opt._data!!._responseBody!!._userInfo!!._mobileNo!!)
                                appSettings.setValue(AppSettingsKey.PARKING_NO,
                                        opt._data!!._responseBody!!._userInfo!!._parkingNo!!)
                                appSettings.setValue(AppSettingsKey.PROJECT_ID,
                                        opt._data!!._responseBody!!._userInfo!!._projectInfo!!._projectID!!)
                                appSettings.setValue(AppSettingsKey.REGISTER_TYPE,
                                        opt._data!!._responseBody!!._userInfo!!._registerType!!)
                                appSettings.setValue(AppSettingsKey.ROLE_ID,
                                        opt._data!!._responseBody!!._userInfo!!._roleInfo!!._roleID!!)
//                                appSettings.setValue(AppSettingsKey.ROOM_ID,
//                                        opt._data!!._responseBody!!._userInfo!!._roomInfo?._roomID!!)
                                appSettings.setValue(AppSettingsKey.ROOM_ID,
                                        opt._data!!._responseBody!!._userInfo!!.let {
                                            if (it._roomInfo != null) {
                                                return@let it._roomInfo?._roomID
                                            } else {
                                                return@let 0
                                            }
                                        }!!
                                )
                                appSettings.setValue(AppSettingsKey.ROOM_NO,
                                        opt._data!!._responseBody!!._userInfo!!.let {
                                            if (it._roomInfo != null) {
                                                return@let it._roomInfo?._roomNo
                                            } else {
                                                return@let ""
                                            }
                                        }!!
                                )
                                appSettings.setValue(AppSettingsKey.USER_ID,
                                        opt._data!!._responseBody!!._userInfo!!._userID!!)
                                appSettings.setValue(AppSettingsKey.USERNAME,
                                        opt._data!!._responseBody!!._userInfo!!._username!!)
//                                appSettings.setValue(AppSettingsKey.BUILDING_ID,
//                                        opt._data!!._responseBody!!._userInfo!!._buildingInfo?._buildingID!!)
                                appSettings.setValue(AppSettingsKey.BUILDING_ID,
                                        opt._data!!._responseBody!!._userInfo!!.let {
                                            if (it._buildingInfo != null) {
                                                return@let it._buildingInfo?._buildingID
                                            } else {
                                                return@let 0
                                            }
                                        }!!
                                )
                                appSettings.setValue(AppSettingsKey.PASSWORD,
                                        loginMobileRequest.password)

                                mutableStatus.value = Status.Success(
                                        currentValue.viewDataBundle.copy(status_msg = opt._data!!._responseHeader!!._responseDesc)
                                )
                            } else {
                                mutableStatus.value = Status.Error(
                                        opt._data!!._responseHeader!!._responseDesc!!,
                                        currentValue.viewDataBundle.copy(
                                                status_msg = opt._data!!._responseHeader!!._responseDesc
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
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val status_msg: String? = "")
}