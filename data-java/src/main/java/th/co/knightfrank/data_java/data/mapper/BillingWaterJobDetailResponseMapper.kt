package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.BillingWaterJobDetailInfoParcel
import th.co.knightfrank.data_java.data.responses.billings.BillingWaterJobDetailDataInfoResponse

class BillingWaterJobDetailResponseMapper : Mapper<BillingWaterJobDetailDataInfoResponse?, BillingWaterJobDetailInfoParcel?> {
    override fun transform(source: BillingWaterJobDetailDataInfoResponse?): BillingWaterJobDetailInfoParcel = BillingWaterJobDetailInfoParcel(
            _billingWaterJobDetailID = source?._billingWaterJobDetailID,
            _billingWaterJobID = source?._billingWaterJobID,
            _inputDate = source?._inputDate,
            _meterNo = source?._meterNo,
            _roomID = source?._roomID,
            _roomNo = source?._roomNo,
            _roomWaterMeterNo = source?._roomWaterMeterNo
    )
}