package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.BillingWaterJobInfoParcel
import th.co.knightfrank.data_java.data.responses.billings.BillingWaterJobDataInfoResponse

class BillingWaterJobResponseMapper : Mapper<BillingWaterJobDataInfoResponse?, BillingWaterJobInfoParcel?> {
    override fun transform(source: BillingWaterJobDataInfoResponse?): BillingWaterJobInfoParcel = BillingWaterJobInfoParcel(
            _billingMonthName = source?._billingMonthName,
            _billingMonthNo = source?._billingMonthNo,
            _billingYear = source?._billingYear,
            _billingPeriod = source?._billingPeriod,
            _billingWaterJobID = source?._billingWaterJobID,
            _createAt = source?._createAt,
            _createAtDisplay = source?._createAtDisplay,
            _projectID = source?._projectID,
            _roomQty = source?._roomQty,
            _roomTotal = source?._roomTotal
    )
}