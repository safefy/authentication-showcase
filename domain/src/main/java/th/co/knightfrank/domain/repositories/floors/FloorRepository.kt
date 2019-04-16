package th.co.knightfrank.domain.repositories.floors

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorListRequest
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorRegisterListRequest
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorListContainerResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorListWrapperResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorRegisterListContainerResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorRegisterListWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FloorRepository
@Inject
constructor(private val api: API) {

    fun inquiryMasterFloorList(inquiryMasterFloorListRequest: InquiryMasterFloorListRequest): Single<InquiryMasterFloorListWrapperResponse<InquiryMasterFloorListContainerResponse>> {
        return api.inquiryMasterFloorList(inquiryMasterFloorListRequest)
    }

    fun inquiryMasterFloorRegisterList(inquiryMasterFloorRegisterListRequest: InquiryMasterFloorRegisterListRequest): Single<InquiryMasterFloorRegisterListWrapperResponse<InquiryMasterFloorRegisterListContainerResponse>> {
        return api.inquiryMasterFloorRegisterList(inquiryMasterFloorRegisterListRequest)
    }

}