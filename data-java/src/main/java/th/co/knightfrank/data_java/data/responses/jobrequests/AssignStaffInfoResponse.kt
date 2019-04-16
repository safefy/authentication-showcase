package th.co.knightfrank.data_java.data.responses.jobrequests

import com.squareup.moshi.Json

data class AssignStaffInfoResponse(@Json(name = "Email") val _email: String? = null,
                                   @Json(name = "FirebaseToken") val _firebaseToken: String? = null,
                                   @Json(name = "FirstName") val _firstName: String? = "",
                                   @Json(name = "ImagePath") val _imagePath: String? = null,
                                   @Json(name = "LastName") val _lastName: String? = "",
                                   @Json(name = "LineID") val _lineID: String? = null,
                                   @Json(name = "MobileNo") val _mobileNo: String? = null,
                                   @Json(name = "UserID") val _userID: Int? = null)