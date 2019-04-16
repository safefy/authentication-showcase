package th.co.knightfrank.data_java.data.requests.users

import com.squareup.moshi.Json

data class RegisterCustomerRequest(@Json(name = "UserID") val userID: Int,
                                   @Json(name = "FirstName") val firstName: String,
                                   @Json(name = "LastName") val lastName: String,
                                   @Json(name = "MobileNo") val mobileNo: String,
                                   @Json(name = "Email") val email: String,
                                   @Json(name = "LineID") val lineID: String,
                                   @Json(name = "Psswd") val password: String,
                                   @Json(name = "RegisterType") val registerType: Int,
                                   @Json(name = "ParkingNo") val parkingNo: String,
                                   @Json(name = "CarRegistration") val carRegistration: String,
                                   @Json(name = "IsContactLine") val isContactLine: Boolean,
                                   @Json(name = "IsContactMobile") val isContactMobile: Boolean,
                                   @Json(name = "ProjectID") val projectID: Int,
                                   @Json(name = "BuildingID") val buildingID: Int,
                                   @Json(name = "FloorID") val floorID: Int,
                                   @Json(name = "RoomID") val roomID: Int,
                                   @Json(name = "FirebaseToken") val firebaseToken: String,
                                   @Json(name = "InvitationCode") val invitationCode: String)