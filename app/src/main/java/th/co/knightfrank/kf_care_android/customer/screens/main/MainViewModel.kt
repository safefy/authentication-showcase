package th.co.knightfrank.kf_care_android.customer.screens.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
//import th.co.knightfrank.data_java.data.mapper.DashboardItemResponseMapper
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardInfoResponse
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardPinInfoResponse
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.repositories.dashboards.DashboardRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val dashboardRepository: DashboardRepository,
            private val appSettings: AppSettings)
    : DisposableViewModel(), StatusViewModel<MainViewModel.Status, MainViewModel.ViewDataBundle> {

    //private val dashboardItemResponseMapper = DashboardItemResponseMapper()

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        Log.e("MainViewModel", "init")
        mutableStatus.value = Status.Stale(ViewDataBundle(null, listOf()))
    }

    @MainThread
    fun inquiryDashboardRequest(inquiryDashboardListRequest: InquiryDashboardListRequest) {
        Log.e("MainViewModel", "inquiryDashboardRequest start")
        mutableStatus.value = Status.Loading(ViewDataBundle(null, listOf()))
        addDisposable(
                dashboardRepository.inquiryDashboardList(inquiryDashboardListRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    //val itemList = getDashboardItemListFromResponse(opt._data!!._responseBody!!._announcesList!!)
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    dashboardPinList = opt._data!!._responseBody!!._dashboardPin,
                                                    dashboardItemList = opt._data!!._responseBody!!._dashboardList
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    dashboardItemList = listOf(),
                                                    dashboardPinList = listOf()
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    dashboardItemList = listOf(),
                                                    dashboardPinList = listOf()
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong, Internet not connected", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun logout() {
        Log.e("MainViewModel", "logout start")

        appSettings.clearValue(AppSettingsKey.LOGIN_TOKEN)
        appSettings.clearValue(AppSettingsKey.CAR_REGISTRATION)
        appSettings.clearValue(AppSettingsKey.EMAIL)
        appSettings.clearValue(AppSettingsKey.FIREBASE_TOKEN)
        appSettings.clearValue(AppSettingsKey.FIRST_NAME)
        appSettings.clearValue(AppSettingsKey.IMAGE_PATH)
        appSettings.clearValue(AppSettingsKey.IS_CONTACT_LINE)
        appSettings.clearValue(AppSettingsKey.IS_CONTACT_MOBILE)
        appSettings.clearValue(AppSettingsKey.LAST_NAME)
        appSettings.clearValue(AppSettingsKey.LINE_ID)
        appSettings.clearValue(AppSettingsKey.MOBILE_NO)
        appSettings.clearValue(AppSettingsKey.PARKING_NO)
        appSettings.clearValue(AppSettingsKey.PROJECT_ID)
        appSettings.clearValue(AppSettingsKey.REGISTER_TYPE)
        appSettings.clearValue(AppSettingsKey.ROLE_ID)
        appSettings.clearValue(AppSettingsKey.ROOM_ID)
        appSettings.clearValue(AppSettingsKey.USER_ID)
        appSettings.clearValue(AppSettingsKey.USERNAME)
        appSettings.clearValue(AppSettingsKey.BUILDING_ID)
        appSettings.clearValue(AppSettingsKey.ROOM_NO)

        mutableStatus.value = Status.Logout("Logout Success", ViewDataBundle())
    }

//    private fun getDashboardItemListFromResponse(announcementInfoResponseList: List<AnnouncementInfoResponse>): List<DashboardItemParcel>{
//        val dashboardItemList: MutableList<DashboardItemParcel> = mutableListOf()
//        announcementInfoResponseList.forEach {
//            dashboardItemList.add(
//                    dashboardItemResponseMapper.transform(it)
//            )
//        }
//        return dashboardItemList
//    }

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
                              val dashboardPinList: List<DashboardPinInfoResponse>? = listOf<DashboardPinInfoResponse>(),
                              val dashboardItemList: List<DashboardInfoResponse>? = listOf<DashboardInfoResponse>())
}