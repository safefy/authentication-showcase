package th.co.knightfrank.data_java.data.resources.users

import com.squareup.moshi.Json
import org.threeten.bp.OffsetDateTime

data class UserInfoResource
constructor(@Json(name = "CarRegistration") val _carRegistration: String? = null,
            @Json(name = "CreateAt") val _createAt: OffsetDateTime? = null,
            @Json(name = "Email") val _email: String? = null,
            @Json(name = "FirebaseToken") val _firebaseToken: String? = null,
            @Json(name = "FirstName") val _firstName: String? = null,
            @Json(name = "ImagePath") val _imagePath: String? = null,
            @Json(name = "IsContactLine") val _isContactLine: Boolean? = null,
            @Json(name = "IsContactMobile") val _isContactMobile: Boolean? = null,
            @Json(name = "LastLoginTime") val _lastLoginTime: OffsetDateTime? = null,
            @Json(name = "LastName") val _lastName: String? = null,
            @Json(name = "LineID") val _lineID: String? = null,
            @Json(name = "MobileNo") val _mobileNo: String? = null,
            @Json(name = "ParkingNo") val _parkingNo: String? = null,
            @Json(name = "ProjectID") val _projectID: Int? = null,
            @Json(name = "RegisterType") val _registerType: Int? = null,
            @Json(name = "RoleID") val _roleID: Int? = null,
            @Json(name = "RoomID") val _roomID: Int? = null,
            @Json(name = "UpdateAt") val _updateAt: OffsetDateTime? = null,
            @Json(name = "UserID") val _userID: Int? = null,
            @Json(name = "Username") val _username: String? = null)
