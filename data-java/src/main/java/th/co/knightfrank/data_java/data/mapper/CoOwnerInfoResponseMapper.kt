package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.CoOwnerTypeInfoParcel
import th.co.knightfrank.data_java.data.responses.users.CoOwnerTypeInfoResponse

class CoOwnerInfoResponseMapper : Mapper<CoOwnerTypeInfoResponse?, CoOwnerTypeInfoParcel?> {
    override fun transform(source: CoOwnerTypeInfoResponse?): CoOwnerTypeInfoParcel = CoOwnerTypeInfoParcel(
            _coOwnerTypeID = source?._coOwnerTypeID,
            _coOwnerTypeName = source?._CoOwnerTypeName
    )
}