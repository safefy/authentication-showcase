package th.co.knightfrank.domain.repositories.rooms

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.rooms.GetMasterRoomInfoRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomListByRoomNameRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomRegisterListRequest
import th.co.knightfrank.data_java.data.responses.rooms.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RoomRepository
@Inject
constructor(private val api: API) {

    fun getMasterRoomInfo(getMasterRoomInfoRequest: GetMasterRoomInfoRequest): Single<GetMasterRoomInfoWrapperResponse<GetMasterRoomInfoContainerResponse>> {
        return api.getMasterRoomInfo(getMasterRoomInfoRequest)
    }

    fun inquiryMasterRoomListByRoomName(inquiryMasterRoomListByRoomNameRequest: InquiryMasterRoomListByRoomNameRequest): Single<InquiryMasterRoomListByRoomNameWrapperResponse<InquiryMasterRoomListByRoomNameContainerResponse>> {
        return api.inquiryMasterRoomListByRoomName(inquiryMasterRoomListByRoomNameRequest)
    }

    fun inquiryMasterRoomRegisterList(inquiryMasterRoomRegisterListRequest: InquiryMasterRoomRegisterListRequest): Single<InquiryMasterRoomRegisterListWrapperResponse<InquiryMasterRoomRegisterListContainerResponse>> {
        return api.inquiryMasterRoomRegisterList(inquiryMasterRoomRegisterListRequest)
    }
}