package th.co.knightfrank.domain.repositories.parcels

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.parcels.*
import th.co.knightfrank.data_java.data.responses.parcels.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParcelRepository
@Inject
constructor(private val api: API) {

    fun getParcelListByUserID(getParcelListByUserIDRequest: GetParcelListByUserIDRequest): Single<GetParcelListByUserIDWrapperResponse<GetParcelListByUserIDContainerResponse>> {
        return api.getParcelListByUserID(getParcelListByUserIDRequest)
    }

    fun getParcelDetail(getParcelDetailRequest: GetParcelDetailRequest): Single<GetParcelDetailWrapperResponse<GetParcelDetailContainerResponse>> {
        return api.getParcelDetail(getParcelDetailRequest)
    }

    fun registerParcel(registerParcelRequest: RegisterParcelRequest): Single<RegisterParcelWrapperResponse<RegisterParcelContainerResponse>> {
        return api.registerParcel(registerParcelRequest)
    }

    fun checkoutParcel(checkoutParcelRequest: CheckoutParcelRequest): Single<CheckoutParcelWrapperResponse<CheckoutParcelContainerResponse>> {
        return api.checkoutParcel(checkoutParcelRequest)
    }

    fun inquiryMasterParcelProviderList(inquiryMasterParcelProviderListRequest: InquiryMasterParcelProviderListRequest): Single<InquiryMasterParcelProviderListWrapperResponse<InquiryMasterParcelProviderListContainerResponse>> {
        return api.inquiryMasterParcelProviderList(inquiryMasterParcelProviderListRequest)
    }

    fun inquiryMasterParcelTypeList(inquiryMasterParcelTypeListRequest: InquiryMasterParcelTypeListRequest): Single<InquiryMasterParcelTypeListWrapperResponse<InquiryMasterParcelTypeListContainerResponse>> {
        return api.inquiryMasterParcelTypeList(inquiryMasterParcelTypeListRequest)
    }

}