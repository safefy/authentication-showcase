package th.co.knightfrank.data_java.data.responses.roles

import com.squareup.moshi.Json

data class RoleInfoResponse(
        @Json(name = "RoleID") val _roleID: Int? = 0,
        @Json(name = "RoleName") val _roleName: String? = null
)