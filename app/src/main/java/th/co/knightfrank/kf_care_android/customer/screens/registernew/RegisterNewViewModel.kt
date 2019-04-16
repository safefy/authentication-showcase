package th.co.knightfrank.kf_care_android.customer.screens.registernew

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingRegisterListRequest
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorRegisterListRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomRegisterListRequest
import th.co.knightfrank.data_java.data.requests.users.InquiryMasterProjectRegisterListRequest
import th.co.knightfrank.data_java.data.requests.users.RegisterNewRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.buildings.BuildingInfoResponse
import th.co.knightfrank.data_java.data.responses.floors.FloorInfoResponse
import th.co.knightfrank.data_java.data.responses.rooms.RoomsInfoResponse
import th.co.knightfrank.data_java.data.responses.users.RegisterNewResponse
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.repositories.buildings.BuildingRepository
import th.co.knightfrank.domain.repositories.floors.FloorRepository
import th.co.knightfrank.domain.repositories.rooms.RoomRepository
import th.co.knightfrank.domain.repositories.users.UserRepository
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class RegisterNewViewModel
@Inject
constructor(private val userRepository: UserRepository,
            private val appSettings: AppSettings,
            private val buildingRepository: BuildingRepository,
            private val floorRepository: FloorRepository,
            private val roomRepository: RoomRepository)
    : DisposableViewModel(), StatusViewModel<RegisterNewViewModel.Status, RegisterNewViewModel.ViewDataBundle> {

    private val mutableStatus = MutableLiveData<Status>()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun registerNewCustomer(registerNewCustomerRequest: RegisterNewRequest) {
        mutableStatus.value = Status.Loading(ViewDataBundle())

        addDisposable(
                userRepository.registerNewCustomer(registerNewCustomerRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            if (opt._data!!._responseHeader!!._responseCode == "000") {

                                appSettings.setValue(AppSettingsKey.LOGIN_TOKEN,
                                        opt._data!!._responseBody!!._userToken!!)
                                appSettings.setValue(AppSettingsKey.CAR_REGISTRATION,
                                        registerNewCustomerRequest.carRegistration)
                                appSettings.setValue(AppSettingsKey.EMAIL,
                                        registerNewCustomerRequest.email)
                                appSettings.setValue(AppSettingsKey.FIREBASE_TOKEN,
                                        registerNewCustomerRequest.firebaseToken)
                                appSettings.setValue(AppSettingsKey.FIRST_NAME,
                                        registerNewCustomerRequest.firstName)
                                appSettings.setValue(AppSettingsKey.IMAGE_PATH,
                                        "")
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_LINE,
                                        registerNewCustomerRequest.isContactLine)
                                appSettings.setValue(AppSettingsKey.IS_CONTACT_MOBILE,
                                        registerNewCustomerRequest.isContactMobile)
                                appSettings.setValue(AppSettingsKey.LAST_NAME,
                                        registerNewCustomerRequest.lastName)
                                appSettings.setValue(AppSettingsKey.LINE_ID,
                                        registerNewCustomerRequest.lineID)
                                appSettings.setValue(AppSettingsKey.MOBILE_NO,
                                        registerNewCustomerRequest.mobileNo)
                                appSettings.setValue(AppSettingsKey.PARKING_NO,
                                        registerNewCustomerRequest.parkingNo)
                                appSettings.setValue(AppSettingsKey.PROJECT_ID,
                                        registerNewCustomerRequest.projectID)
                                appSettings.setValue(AppSettingsKey.REGISTER_TYPE,
                                        registerNewCustomerRequest.registerType)
                                appSettings.setValue(AppSettingsKey.ROLE_ID,
                                        opt._data!!._responseBody!!._userRoleID!!)
                                appSettings.setValue(AppSettingsKey.ROOM_ID,
                                        registerNewCustomerRequest.roomID)
                                appSettings.setValue(AppSettingsKey.USERNAME,
                                        registerNewCustomerRequest.email)
                                appSettings.setValue(AppSettingsKey.BUILDING_ID,
                                        registerNewCustomerRequest.buildingID)

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

    @MainThread
    fun inquiryMasterProjectRegisterListRequest(inquiryMasterProjectRegisterListRequest: InquiryMasterProjectRegisterListRequest) {
        mutableStatus.value = RegisterNewViewModel.Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                userRepository.inquiryProjectRegisterList(
                        inquiryMasterProjectRegisterListRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {

                                opt._data!!._responseHeader!!._responseCode == "000" -> {


                                    if (opt._data?._recordCount == 0) {
                                        mutableStatus.value = Status.SuccessWithNoData(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        projectList = opt._data?._projectList,
                                                        buildingList = null
                                                )
                                        )
                                        Log.e("SelectProject", "Data : " + opt._data?._projectList)
                                    } else {
                                        mutableStatus.value = RegisterNewViewModel.Status.LoadProjectSuccess(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        projectList = opt._data?._projectList,
                                                        buildingList = mutableListOf()
                                                )
                                        )
                                    }


                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    projectList = null,
                                                    buildingList = null
                                            )
                                    )
                                else ->
                                    mutableStatus.value = RegisterNewViewModel.Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    projectList = null,
                                                    buildingList = null
                                            )
                                    )
                            }

                        }, {
                            mutableStatus.value = RegisterNewViewModel.Status.ErrorException("Something went wrong", RegisterNewViewModel.ViewDataBundle())
                        })
        )

    }

    @MainThread
    fun inquiryMasterBuildingRegisterListRequest(inquiryMasterBuildingRegisterListRequest: InquiryMasterBuildingRegisterListRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                buildingRepository.inquiryMasterBuildingRegisterList(
                        inquiryMasterBuildingRegisterListRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    Log.e("SelectBuilding", "Data : " + opt._data?._buildingList)
                                    if (opt._data?._recordCount == 0) {
                                        mutableStatus.value = Status.SuccessWithNoData(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        buildingList = null,
                                                        floorList = null
                                                )
                                        )
                                        //return@subscribe
                                    } else {
                                        mutableStatus.value = Status.LoadBuildingSuccess(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        buildingList = opt._data?._buildingList,
                                                        floorList = mutableListOf()
                                                )
                                        )
                                    }
                                }

                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    buildingList = null,
                                                    floorList = null
                                            )
                                    )

                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    buildingList = null,
                                                    floorList = null
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun inquiryMasterFloorRegisterListRequest(inquiryMasterFloorRegisterListRequest: InquiryMasterFloorRegisterListRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                floorRepository.inquiryMasterFloorRegisterList(
                        inquiryMasterFloorRegisterListRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    Log.e("SelectFloor", "Data : " + opt._data!!._responseHeader!!._responseCode)
                                    Log.e("SelectFloor", "Data : " + opt._data?._floorInfoList)
                                    if (opt._data?._recordCount == 0) {
                                        mutableStatus.value = Status.SuccessWithNoDataFloor(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        count = "0",
                                                        floorList = opt._data?._floorInfoList,
                                                        roomList = null
                                                )
                                        )
                                    } else {
                                        mutableStatus.value = Status.LoadFloorSuccess(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        floorList = opt._data?._floorInfoList,
                                                        roomList = mutableListOf()
                                                )
                                        )
                                    }

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    floorList = null,
                                                    roomList = null
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    //buildingList = null,
                                                    floorList = null,
                                                    roomList = null
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun inquiryMasterRoomRegisterListRequest(inquiryMasterRoomRegisterListRequest: InquiryMasterRoomRegisterListRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                roomRepository.inquiryMasterRoomRegisterList(
                        inquiryMasterRoomRegisterListRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    Log.e("SelectRoom", "Data : " + opt._data!!._responseHeader!!._responseCode)
                                    Log.e("SelectRoom", "Data : " + opt._data?._roomInfoList)
                                    if (opt._data?._recordCount == 0) {
                                        mutableStatus.value = Status.SuccessWithNoDataRooms(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        roomList = opt._data?._roomInfoList
                                                )
                                        )
                                    } else {
                                        mutableStatus.value = Status.LoadRoomSuccess(
                                                currentValue.viewDataBundle.copy(
                                                        headerResponse = opt._data!!._responseHeader!!,
                                                        roomList = opt._data?._roomInfoList
                                                )
                                        )
                                    }

                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    roomList = null
                                            )
                                    )
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    roomList = null
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
        class Logout(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SuccessWithNoData(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SuccessWithNoDataFloor(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class LoadProjectSuccess(viewDataBundle: RegisterNewViewModel.ViewDataBundle) : RegisterNewViewModel.Status(viewDataBundle)
        class LoadBuildingSuccess(viewDataBundle: RegisterNewViewModel.ViewDataBundle) : RegisterNewViewModel.Status(viewDataBundle)
        class LoadFloorSuccess(viewDataBundle: RegisterNewViewModel.ViewDataBundle) : RegisterNewViewModel.Status(viewDataBundle)
        class LoadRoomSuccess(viewDataBundle: RegisterNewViewModel.ViewDataBundle) : RegisterNewViewModel.Status(viewDataBundle)
        class SuccessWithNoDataRooms(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val status_msg: String? = "",
                              val projectList: MutableList<RegisterNewResponse>? = mutableListOf(),
                              val buildingList: MutableList<BuildingInfoResponse>? = mutableListOf(),
                              val floorList: MutableList<FloorInfoResponse>? = mutableListOf(),
                              val roomList: MutableList<RoomsInfoResponse>? = mutableListOf(),
                              val headerResponse: HeaderResponse? = null,
                              val jobRequestDetailData: RegisterNewResponse? = null,
                              val count: String? = ""
    )
}