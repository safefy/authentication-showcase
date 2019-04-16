package th.co.knightfrank.kf_care_android.customer.screens.main.dashboardsearch

//import th.co.knightfrank.data_java.data.mapper.DashboardItemResponseMapper
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardInfoResponse
import th.co.knightfrank.data_java.data.responses.dashboards.DashboardPinInfoResponse
import th.co.knightfrank.domain.repositories.dashboards.DashboardRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class DashboardSearchViewModel
@Inject
constructor(private val dashboardRepository: DashboardRepository,
            private val appSettings: AppSettings)
    : DisposableViewModel(), StatusViewModel<DashboardSearchViewModel.Status, DashboardSearchViewModel.ViewDataBundle> {

    //private val dashboardItemResponseMapper = DashboardItemResponseMapper()

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle(null, listOf()))
    }

    @MainThread
    fun inquiryDashboardRequest(inquiryDashboardListRequest: InquiryDashboardListRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle(null, listOf()))
        addDisposable(
                dashboardRepository.inquiryDashboardList(inquiryDashboardListRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    dashboardPinList = opt._data!!._responseBody!!._dashboardPin,
                                                    dashboardItemList = opt._data!!._responseBody!!._dashboardList,
                                                    dashboardFilterList = let {
                                                        val list: MutableList<DashboardInfoResponse>? = mutableListOf()
                                                        opt._data!!._responseBody!!._dashboardList?.forEach {
                                                            list?.add(it)
                                                        }
                                                        return@let list
                                                    }
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
    fun filteringDashboardList(text: String?) {
        val filterText = text?.toLowerCase()
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)

        val allDataList = let {
            val list: MutableList<DashboardInfoResponse>? = mutableListOf()
            currentValue.viewDataBundle.dashboardItemList?.forEach {
                list?.add(it)
            }
            return@let list
        }

        val filterList: MutableList<DashboardInfoResponse>? = mutableListOf()

        if (filterText.isNullOrEmpty()) {
            mutableStatus.value = Status.FilteredSuccess(
                    currentValue.viewDataBundle.copy(
                            dashboardFilterList = allDataList
                    )
            )
        } else {
            allDataList?.forEach {
                if ((it._title?.toLowerCase()?.contains(filterText.toString())!!) || (it._detail?.toLowerCase()?.contains(filterText.toString())!!)) {
                    filterList?.add(it)
                }
            }

            if (filterList?.isNotEmpty()!!) {
                mutableStatus.value = Status.FilteredSuccess(
                        currentValue.viewDataBundle.copy(
                                dashboardFilterList = filterList
                        )
                )
            } else {
                mutableStatus.value = Status.FilteredNoDataFound("Data not found!!!", ViewDataBundle())
            }
        }
    }


    sealed class Status(viewDataBundle: ViewDataBundle) : StatusViewModel.Status<ViewDataBundle>(viewDataBundle) {
        class Stale(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Loading(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Success(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SuccessWithNoData(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Logout(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)

        class FilteredSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class FilteredNoDataFound(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val headerResponse: HeaderResponse? = null,
                              val dashboardPinList: List<DashboardPinInfoResponse>? = listOf<DashboardPinInfoResponse>(),
                              val dashboardItemList: List<DashboardInfoResponse>? = listOf<DashboardInfoResponse>(),
                              val dashboardFilterList: MutableList<DashboardInfoResponse>? = mutableListOf())
}