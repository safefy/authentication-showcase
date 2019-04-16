package th.co.knightfrank.data_java.data.requests.billings

import com.squareup.moshi.Json

data class BillingWaterJobDataInfoRequest(@Json(name = "BuildingID") val buildingID: Int?,
                                          @Json(name = "FloorID") val floorID: Int?,
                                          @Json(name = "BillingWaterJobID") val billingWaterJobID: Int?)
