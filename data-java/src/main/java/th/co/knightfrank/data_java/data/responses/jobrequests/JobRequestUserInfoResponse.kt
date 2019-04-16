package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class JobRequestUserInfoResponse(
        @Json(name = "CarRegistration") val _carRegistration: String? = null,
        @Json(name = "Email") val _email: String? = null,
        @Json(name = "FirebaseToken") val _firebaseToken: String? = null,
        @Json(name = "FirstName") val _firstName: String? = null,
        @Json(name = "ImagePath") val _imagePath: String? = null,
        @Json(name = "IsContactLine") val _isContactLine: Boolean? = null,
        @Json(name = "IsContactMobile") val _isContactMobile: Boolean? = null,
        @Json(name = "LastName") val _lastName: String? = null,
        @Json(name = "LineID") val _lineID: String? = null,
        @Json(name = "MobileNo") val _mobileNo: String? = null,
        @Json(name = "ParkingNo") val _parkingNo: String? = null,
        @Json(name = "ProjectID") val _projectID: Int? = null,
        @Json(name = "RoleID") val _roleID: Int? = null,
        @Json(name = "RoomID") val _roomID: Int? = null,
        @Json(name = "UserID") val _userID: Int? = null
)