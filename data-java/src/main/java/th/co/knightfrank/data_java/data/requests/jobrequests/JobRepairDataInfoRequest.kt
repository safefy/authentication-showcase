package th.co.knightfrank.data_java.data.requests.jobrequests

import com.squareup.moshi.Json
import th.co.knightfrank.data_java.data.requests.ImageFileInfoRequest

data class JobRepairDataInfoRequest(@Json(name = "JobRequestID") val jobRequestID: Int,
                                    @Json(name = "ActionTime") val actionTime: String,
                                    @Json(name = "IsComplete") val isComplete: Boolean,
                                    @Json(name = "ActionDetail") val actionDetail: String,
                                    @Json(name = "IsFinishJob") val isFinishJob: Boolean,
                                    @Json(name = "ImageFileList") val imageFileList: MutableList<ImageFileInfoRequest>?)