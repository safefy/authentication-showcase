package th.co.knightfrank.domain.repositories.billings


import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.billings.*
import th.co.knightfrank.data_java.data.responses.billings.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BillingRepository
@Inject
constructor(private val api: API) {

    fun getWaterBillingJobList(getWaterBillingJobListRequest: GetWaterBillingJobListRequest): Single<GetWaterBillingJobListWrapperResponse<GetWaterBillingJobListContainerResponse>> {
        return api.getWaterBillingJobList(getWaterBillingJobListRequest)
    }

    fun getRoomWaterMeterJobList(getRoomWaterMeterJobListRequest: GetRoomWaterMeterJobListRequest): Single<GetRoomWaterMeterJobListWrapperResponse<GetRoomWaterMeterJobListContainerResponse>> {
        return api.getRoomWaterMeterJobList(getRoomWaterMeterJobListRequest)
    }

    fun saveRoomWaterMeter(saveRoomWaterMeterRequest: SaveRoomWaterMeterRequest): Single<SaveRoomWaterMeterWrapperResponse<SaveRoomWaterMeterContainerResponse>> {
        return api.saveRoomWaterMeter(saveRoomWaterMeterRequest)
    }

    fun getBillingWaterJobInvoiceDetail(getBillingWaterJobInvoiceDetailRequest: GetBillingWaterJobInvoiceDetailRequest): Single<GetBillingWaterJobInvoiceDetailWrapperResponse<GetBillingWaterJobInvoiceDetailContainerResponse>> {
        return api.getBillingWaterJobInvoiceDetail(getBillingWaterJobInvoiceDetailRequest)
    }

    fun informPayment(informPaymentRequest: InformPaymentRequest): Single<InformPaymentWrapperResponse<InformPaymentContainerResponse>> {
        return api.informPayment(informPaymentRequest)
    }

    fun getBillingCentralExpenseInvoiceDetail(getBillingCentralExpenseInvoiceDetailRequest: GetBillingCentralExpenseInvoiceDetailRequest): Single<GetBillingCentralExpenseInvoiceDetailWrapperResponse<GetBillingCentralExpenseInvoiceDetailContainerResponse>> {
        return api.getBillingCentralExpenseInvoiceDetail(getBillingCentralExpenseInvoiceDetailRequest)
    }

}