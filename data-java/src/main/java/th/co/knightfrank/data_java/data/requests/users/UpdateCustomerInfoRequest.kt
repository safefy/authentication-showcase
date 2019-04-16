package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class UpdateCustomerInfoRequest(@Json(name = "UserID") val _userID: Int,
                                     @Json(name = "FirstName") val _firstName: String?,
                                     @Json(name = "LastName") val _lastName: String?,
                                     @Json(name = "MobileNo") val _mobileNo: String?,
                                     @Json(name = "Email") val _email: String?,
                                     @Json(name = "LineID") val _lineID: String?,
                                     @Json(name = "RegisterType") val _registerType: Int?,
                                     @Json(name = "ParkingNo") val _parkingNo: String?,
                                     @Json(name = "CarRegistration") val _carRegistration: String?,
                                     @Json(name = "IsContactLine") val _isContactLine: Boolean?,
                                     @Json(name = "IsContactMobile") val _isContactMobile: Boolean?,
                                     @Json(name = "ProjectID") val _projectID: Int?,
                                     @Json(name = "BuildingID") val _buildingID: Int?,
                                     @Json(name = "FloorID") val _floorID: Int?,
                                     @Json(name = "RoomID") val _roomID: Int?,
                                     @Json(name = "FirebaseToken") val _firebaseToken: String?,
                                     @Json(name = "ImageFileName") val _imageFileName: String?,
                                     @Json(name = "ImageContent") val _imageContent: String?,
                                     @Json(name = "OldPassword") val _oldPassword: String?,
                                     @Json(name = "NewPassword") val _newPassword: String?,
                                     @Json(name = "ConfirmPassword") val _confirmPassword: String?,
                                     @Json(name = "token") val _token: String?)