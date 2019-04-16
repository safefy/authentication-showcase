package th.co.knightfrank.domain.repositories.buildings

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingListRequest
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingRegisterListRequest
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingListContainerResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingListWrapperResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingRegisterListContainerResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingRegisterListWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildingRepository
@Inject
constructor(private val api: API) {

    fun inquiryMasterBuildingList(inquiryMasterBuildingListRequest: InquiryMasterBuildingListRequest): Single<InquiryMasterBuildingListWrapperResponse<InquiryMasterBuildingListContainerResponse>> {
        return api.inquiryMasterBuildingList(inquiryMasterBuildingListRequest)
    }

    fun inquiryMasterBuildingRegisterList(inquiryMasterBuildingRegisterListRequest: InquiryMasterBuildingRegisterListRequest): Single<InquiryMasterBuildingRegisterListWrapperResponse<InquiryMasterBuildingRegisterListContainerResponse>> {
        return api.inquiryMasterBuildingRegisterList(inquiryMasterBuildingRegisterListRequest)
    }
}