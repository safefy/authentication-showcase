package th.co.knightfrank.kf_care_android.customer.screens.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.users.RegisterCustomerRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.repositories.users.UserRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class RegisterViewModel
@Inject
constructor(private val userRepository: UserRepository,
            private val appSettings: AppSettings)
    : DisposableViewModel(), StatusViewModel<RegisterViewModel.Status, RegisterViewModel.ViewDataBundle> {

    private val mutableStatus = MutableLiveData<Status>()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun registerCustomer(registerCustomerRequest: RegisterCustomerRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())

        addDisposable(
                userRepository.registerCustomer(registerCustomerRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            if (opt._data!!._responseHeader!!._responseCode == "000") {

                                appSettings.setValue(AppSettingsKey.LOGIN_TOKEN,
                                        opt._data!!._responseBody!!._userToken!!)
                                appSettings.setValue(AppSettingsKey.CAR_REGISTRATION,
                                        registerCustomerRequest.carRegistration)
                                appSettings.setValue(AppSettingsKey.EMAIL,
                                        registerCustomerRequest.email)
                                appSettings.setValue(AppSettingsKey.FIREBASE_TOKEN,
                                        registerCustomerRequest.firebaseToken)
                                appSettings.setValue(AppSettingsKey.FIRST_NAME,
                                        registerCustomerRequest.firstName)
                                appSettings.setValue(AppSettingsKey.IMAGE_PATH,
                                        "")
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_LINE,
                                        registerCustomerRequest.isContactLine)
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_MOBILE,
                                        registerCustomerRequest.isContactMobile)
                                appSettings.setValue(AppSettingsKey.LAST_NAME,
                                        registerCustomerRequest.lastName)
                                appSettings.setValue(AppSettingsKey.LINE_ID,
                                        registerCustomerRequest.lineID)
                                appSettings.setValue(AppSettingsKey.MOBILE_NO,
                                        registerCustomerRequest.mobileNo)
                                appSettings.setValue(AppSettingsKey.PARKING_NO,
                                        registerCustomerRequest.parkingNo)
                                appSettings.setValue(AppSettingsKey.PROJECT_ID,
                                        registerCustomerRequest.projectID)
                                appSettings.setValue(AppSettingsKey.REGISTER_TYPE,
                                        registerCustomerRequest.registerType)
                                appSettings.setValue(AppSettingsKey.ROLE_ID,
                                        opt._data!!._responseBody!!._userRoleID!!)
                                appSettings.setValue(AppSettingsKey.ROOM_ID,
                                        registerCustomerRequest.roomID)
                                appSettings.setValue(AppSettingsKey.USER_ID,
                                        registerCustomerRequest.userID)
                                appSettings.setValue(AppSettingsKey.USERNAME,
                                        registerCustomerRequest.email)
                                appSettings.setValue(AppSettingsKey.BUILDING_ID,
                                        registerCustomerRequest.buildingID)

                                mutableStatus.value = Status.Success(
                                        currentValue.viewDataBundle.copy(
                                                status_msg = opt._data!!._responseHeader!!._responseDesc
                                        )
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