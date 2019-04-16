package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.RoleInfoParcel
import th.co.knightfrank.data_java.data.responses.roles.RoleInfoResponse

class RoleInfoResponseMapper : Mapper<RoleInfoResponse?, RoleInfoParcel?> {
    override fun transform(source: RoleInfoResponse?): RoleInfoParcel = RoleInfoParcel(
            _roleID = source?._roleID,
            _roleName = source?._roleName
    )
}