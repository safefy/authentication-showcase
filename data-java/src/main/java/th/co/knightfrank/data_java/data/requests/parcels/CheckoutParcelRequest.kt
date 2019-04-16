package th.co.knightfrank.data_java.data.requests.parcels

import com.squareup.moshi.Json

data class CheckoutParcelRequest(
        @Json(name = "UserID") val _userID: Int,
        @Json(name = "token") val _token: String,
        @Json(name = "CheckoutData") val _checkoutDataInfo: CheckoutDataInfoRequest)