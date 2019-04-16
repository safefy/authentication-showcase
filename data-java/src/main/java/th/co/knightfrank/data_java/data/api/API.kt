package th.co.knightfrank.data_java.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import th.co.knightfrank.data_java.data.requests.DeleteImageRequest
import th.co.knightfrank.data_java.data.requests.announcements.*
import th.co.knightfrank.data_java.data.requests.billings.*
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingListRequest
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingRegisterListRequest
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardRequest
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorListRequest
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorRegisterListRequest
import th.co.knightfrank.data_java.data.requests.jobrequests.*
import th.co.knightfrank.data_java.data.requests.messages.CreateInboxMessageRequest
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageByUserIDRequest
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageDetailRequest
import th.co.knightfrank.data_java.data.requests.messages.ReplyInboxMesssageRequest
import th.co.knightfrank.data_java.data.requests.parcels.*
import th.co.knightfrank.data_java.data.requests.rooms.GetMasterRoomInfoRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomListByRoomNameRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomRegisterListRequest
import th.co.knightfrank.data_java.data.requests.staffs.InquiryMasterStaffByRoleRequest
import th.co.knightfrank.data_java.data.requests.users.*
import th.co.knightfrank.data_java.data.responses.announcements.*
import th.co.knightfrank.data_java.data.responses.billings.*
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingListContainerResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingListWrapperResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingRegisterListContainerResponse
import th.co.knightfrank.data_java.data.responses.buildings.InquiryMasterBuildingRegisterListWrapperResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardContainerResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardListContainerResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardListWrapperResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardWrapperResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorListContainerResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorListWrapperResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorRegisterListContainerResponse
import th.co.knightfrank.data_java.data.responses.floors.InquiryMasterFloorRegisterListWrapperResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.*
import th.co.knightfrank.data_java.data.responses.jobrequests.search.GetJobRequestListContainerResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.search.GetJobRequestListWrapperResponse
import th.co.knightfrank.data_java.data.responses.messages.*
import th.co.knightfrank.data_java.data.responses.parcels.*
import th.co.knightfrank.data_java.data.responses.rooms.*
import th.co.knightfrank.data_java.data.responses.staffs.InquiryMasterStaffByRoleContainerResponse
import th.co.knightfrank.data_java.data.responses.staffs.InquiryMasterStaffByRoleWrapperResponse
import th.co.knightfrank.data_java.data.responses.users.*
import th.co.knightfrank.data_java.data.responses.utils.DeleteImageContainerResponse
import th.co.knightfrank.data_java.data.responses.utils.DeleteImageWrapperResponse


interface API {

    //Auth
    @POST("LoginMobile")
    fun loginMobile(@Body request: LoginMobileRequest): Single<LoginMobileWrapperResponse<LoginMobileContainerResponse>>

    @POST("Login")
    fun login(@Body request: LoginRequest): Single<LoginWrapperResponse<LoginContainerResponse>>
    //fun login(@Body request: LoginRequest): Observable<LoginWrapperResponse<ResponseEntity<LoginResponse>>>

    //User
    @POST("GetUserByInvitationCode")
    fun getUserByInvitationCode(@Body request: GetUserByInvitationCodeRequest): Single<GetUserByInvitationCodeWrapperResponse<GetUserByInvitationCodeContainerResponse>>

    @POST("GetUserByUserID")
    fun getUserByUserID(@Body request: GetUserByUserIDRequest): Single<GetUserByUserIDWrapperResponse<GetUserByInvitationCodeContainerResponse>>

    @POST("RegisterCustomer")
    fun registerCustomer(@Body request: RegisterCustomerRequest): Single<RegisterCustomerWrapperResponse<RegisterCustomerContainerResponse>>

    @POST("RegisterNewCustomer")
    fun registerNewCustomer(@Body request: RegisterNewRequest): Single<RegisterNewCustomerWrapperResponse<RegisterNewCustomerContainerResponse>>


    @POST("InquiryCoOwnerByName")
    fun inquiryCoOwnerByName(@Body request: InquiryCoOwnerByNameRequest): Single<InquiryCoOwnerByNameWrapperResponse<InquiryCoOwnerByNameContainerResponse>>

    @POST("ChangeUserRoom")
    fun changeUserRoom(@Body request: ChangeUserRoomRequest): Single<ChangeUserRoomWrapperResponse<ChangeUserRoomContainerResponse>>

    @POST("ForgetPassword")
    fun forgetPassword(@Body request: ForgetPasswordRequest): Single<ForgetPasswordWrapperResponse<ForgetPasswordContainerResponse>>

    @POST("ResetPassword")
    fun resetPassword(@Body request: ResetPasswordRequest): Single<ResetPasswordWrapperResponse<ResetPasswordContainerResponse>>

    @POST("UpdateCustomerInfo")
    fun updateCustomerInfo(@Body request: UpdateCustomerInfoRequest): Single<UpdateCustomerInfoWrapperResponse<UpdateCustomerInfoContainerResponse>>

    //Dashboard
    @POST("InquiryDashboard")
    fun inquiryDashboard(@Body request: InquiryDashboardRequest): Single<InquiryDashboardWrapperResponse<InquiryDashboardContainerResponse>>

    @POST("InquiryDashboardList")
    fun inquiryDashboardList(@Body request: InquiryDashboardListRequest): Single<InquiryDashboardListWrapperResponse<InquiryDashboardListContainerResponse>>

    //Announcement
    @POST("GetAnnouncementDetail")
    fun getAnnouncementDetail(@Body request: GetAnnouncementDetailRequest): Single<GetAnnouncementDetailWrapperResponse<GetAnnouncementDetailContainerResponse>>

    @POST("AcceptAnnouncement")
    fun acceptAnnouncement(@Body request: AcceptAnnouncementRequest): Single<AcceptAnnouncementWrapperResponse<AcceptAnnouncementContainerResponse>>

    @POST("SearchAnnouncement")
    fun searchAnnouncement(@Body request: SearchAnnouncementRequest): Single<SearchAnnouncementWrapperResponse<SearchAnnouncementContainerResponse>>

    @POST("CreateAnnouncement")
    fun createAnnouncement(@Body request: CreateAnnouncementRequest): Single<CreateAnnouncementWrapperResponse<CreateAnnouncementContainerResponse>>

    @POST("EditAnnouncement")
    fun editAnnouncement(@Body request: EditAnnouncementRequest): Single<EditAnnouncementWrapperResponse<EditAnnouncementContainerResponse>>

    //JobRequest
    @POST("CreateJobRequest")
    fun createJobRequest(@Body request: CreateJobRequestRequest): Single<CreateJobRequestWrapperResponse<CreateJobRequestContainerResponse>>

    @POST("GetJobRequestByStatus")
    fun getJobRequestByStatus(@Body request: GetJobRequestByStatusRequest): Single<GetJobRequestByStatusWrapperResponse<GetJobRequestByStatusContainerResponse>>

    @POST("GetJobRequestByUserID")
    fun getJobRequestByUserID(@Body request: GetJobRequestByUserIDRequest): Single<GetJobRequestByUserIDWrapperResponse<GetJobRequestByStatusContainerResponse>>

    @POST("GetJobRequestDetail")
    fun getJobRequestDetail(@Body request: GetJobRequestDetailRequest): Single<GetJobRequestDetailWrapperResponse<GetJobRequestDetailContainerResponse>>

    @POST("GetJobRequestTransactionList")
    fun getJobRequestTransactionList(@Body request: GetJobRequestTransactionListRequest): Single<GetJobRequestTransactionListWrapperResponse<GetJobRequestTransactionListContainerResponse>>

    @POST("JobApprove")
    fun jobApprove(@Body request: JobApproveRequest): Single<JobApproveWrapperResponse<JobApproveContainerResponse>>

    @POST("JobAssignment")
    fun jobAssignment(@Body request: JobAssignmentRequest): Single<JobAssignmentWrapperResponse<JobAssignmentContainerResponse>>

    @POST("CreateJobRequestByAdmin")
    fun createJobRequestByAdmin(@Body request: CreateJobRequestByAdminRequest): Single<CreateJobRequestByAdminWrapperResponse<CreateJobRequestByAdminContainerResponse>>

    @POST("InquiryMasterJobAreaList")
    fun inquiryMasterJobAreaList(@Body request: InquiryMasterJobAreaListRequest): Single<InquiryMasterJobAreaListWrapperResponse<InquiryMasterJobAreaListContainerResponse>>

    @POST("InquiryMasterJobPriorityList")
    fun inquiryMasterJobPriorityList(@Body request: InquiryMasterJobPriorityListRequest): Single<InquiryMasterJobPriorityListWrapperResponse<InquiryMasterJobPriorityListContainerResponse>>

    @POST("InquiryMasterProjectRegisterList")
    fun inquiryMasterProjectList(@Body request: InquiryMasterProjectRegisterListRequest): Single<InquiryMasterProjectRegisterListWrapperResponse<InquiryMasterProjectRegisterResponse>>


    @POST("InquiryMasterJobSystemTypeList")
    fun inquiryMasterJobSystemTypeList(@Body request: InquiryMasterJobSystemTypeListRequest): Single<InquiryMasterJobSystemTypeListWrapperResponse<InquiryMasterJobSystemTypeListContainerResponse>>

    @POST("JobAccept")
    fun jobAccept(@Body request: JobAcceptRequest): Single<JobAcceptWrapperResponse<JobAcceptContainerResponse>>

    @POST("JobTakeActionResult")
    fun jobTakeActionResult(@Body request: JobTakeActionResultRequest): Single<JobTakeActionResultWrapperResponse<JobTakeActionResultContainerResponse>>

    @POST("JobApproveByManager")
    fun jobApproveByManager(@Body request: JobApproveByManagerRequest): Single<JobApproveByManagerWrapperResponse<JobApproveByManagerContainerResponse>>

    @POST("GetJobRequestList")
    fun getJobRequestList(@Body request: GetJobRequestListRequest): Single<GetJobRequestListWrapperResponse<GetJobRequestListContainerResponse>>

    @POST("InquiryMasterJobStatusList")
    fun inquiryMasterJobStatusList(@Body request: InquiryMasterJobStatusListRequest): Single<InquiryMasterJobStatusListWrapperResponse<InquiryMasterJobStatusListContainerResponse>>

    //Building
    @POST("InquiryMasterBuildingList")
    fun inquiryMasterBuildingList(@Body request: InquiryMasterBuildingListRequest): Single<InquiryMasterBuildingListWrapperResponse<InquiryMasterBuildingListContainerResponse>>

    //Building
    @POST("InquiryMasterBuildingRegisterList")
    fun inquiryMasterBuildingRegisterList(@Body request: InquiryMasterBuildingRegisterListRequest): Single<InquiryMasterBuildingRegisterListWrapperResponse<InquiryMasterBuildingRegisterListContainerResponse>>


    //Floor
    @POST("InquiryMasterFloorList")
    fun inquiryMasterFloorList(@Body request: InquiryMasterFloorListRequest): Single<InquiryMasterFloorListWrapperResponse<InquiryMasterFloorListContainerResponse>>

    //FloorRegis
    @POST("InquiryMasterFloorRegisterList")
    fun inquiryMasterFloorRegisterList(@Body request: InquiryMasterFloorRegisterListRequest): Single<InquiryMasterFloorRegisterListWrapperResponse<InquiryMasterFloorRegisterListContainerResponse>>

    //Room
    @POST("GetMasterRoomInfo")
    fun getMasterRoomInfo(@Body request: GetMasterRoomInfoRequest): Single<GetMasterRoomInfoWrapperResponse<GetMasterRoomInfoContainerResponse>>

    @POST("InquiryMasterRoomListByRoomName")
    fun inquiryMasterRoomListByRoomName(@Body request: InquiryMasterRoomListByRoomNameRequest): Single<InquiryMasterRoomListByRoomNameWrapperResponse<InquiryMasterRoomListByRoomNameContainerResponse>>

    @POST("InquiryMasterRoomRegisterList")
    fun inquiryMasterRoomRegisterList(@Body request: InquiryMasterRoomRegisterListRequest): Single<InquiryMasterRoomRegisterListWrapperResponse<InquiryMasterRoomRegisterListContainerResponse>>


    //Parcel
    @POST("GetParcelListByUserID")
    fun getParcelListByUserID(@Body request: GetParcelListByUserIDRequest): Single<GetParcelListByUserIDWrapperResponse<GetParcelListByUserIDContainerResponse>>

    @POST("GetParcelDetail")
    fun getParcelDetail(@Body request: GetParcelDetailRequest): Single<GetParcelDetailWrapperResponse<GetParcelDetailContainerResponse>>

    @POST("RegisterParcel")
    fun registerParcel(@Body request: RegisterParcelRequest): Single<RegisterParcelWrapperResponse<RegisterParcelContainerResponse>>

    @POST("CheckoutParcel")
    fun checkoutParcel(@Body request: CheckoutParcelRequest): Single<CheckoutParcelWrapperResponse<CheckoutParcelContainerResponse>>

    @POST("InquiryMasterParcelProviderList")
    fun inquiryMasterParcelProviderList(@Body request: InquiryMasterParcelProviderListRequest): Single<InquiryMasterParcelProviderListWrapperResponse<InquiryMasterParcelProviderListContainerResponse>>

    @POST("InquiryMasterParcelTypeList")
    fun inquiryMasterParcelTypeList(@Body request: InquiryMasterParcelTypeListRequest): Single<InquiryMasterParcelTypeListWrapperResponse<InquiryMasterParcelTypeListContainerResponse>>

    //Message
    @POST("CreateInboxMessage")
    fun createInboxMessage(@Body request: CreateInboxMessageRequest): Single<CreateInboxMessageWrapperResponse<CreateInboxMessageContainerResponse>>

    @POST("GetInboxMessageByUserID")
    fun getInboxMessageByUserID(@Body request: GetInboxMessageByUserIDRequest): Single<GetInboxMessageByUserIDWrapperResponse<GetInboxMessageByUserIDContainerResponse>>

    @POST("GetInboxMessageDetail")
    fun getInboxMessageDetail(@Body request: GetInboxMessageDetailRequest): Single<GetInboxMessageDetailWrapperResponse<GetInboxMessageDetailContainerResponse>>

    @POST("ReplyInboxMessage")
    fun replyInboxMessage(@Body request: ReplyInboxMesssageRequest): Single<ReplyInboxMessageWrapperResponse<ReplyInboxMessageContainerResponse>>

    //Billing
    @POST("GetWaterBillingJobList")
    fun getWaterBillingJobList(@Body request: GetWaterBillingJobListRequest): Single<GetWaterBillingJobListWrapperResponse<GetWaterBillingJobListContainerResponse>>

    @POST("GetRoomWaterMeterJobList")
    fun getRoomWaterMeterJobList(@Body request: GetRoomWaterMeterJobListRequest): Single<GetRoomWaterMeterJobListWrapperResponse<GetRoomWaterMeterJobListContainerResponse>>

    @POST("SaveRoomWaterMeter")
    fun saveRoomWaterMeter(@Body request: SaveRoomWaterMeterRequest): Single<SaveRoomWaterMeterWrapperResponse<SaveRoomWaterMeterContainerResponse>>

    @POST("GetBillingWaterJobInvoiceDetail")
    fun getBillingWaterJobInvoiceDetail(@Body request: GetBillingWaterJobInvoiceDetailRequest): Single<GetBillingWaterJobInvoiceDetailWrapperResponse<GetBillingWaterJobInvoiceDetailContainerResponse>>

    @POST("InformPayment")
    fun informPayment(@Body request: InformPaymentRequest): Single<InformPaymentWrapperResponse<InformPaymentContainerResponse>>

    @POST("GetBillingCentralExpenseInvoiceDetail")
    fun getBillingCentralExpenseInvoiceDetail(@Body request: GetBillingCentralExpenseInvoiceDetailRequest): Single<GetBillingCentralExpenseInvoiceDetailWrapperResponse<GetBillingCentralExpenseInvoiceDetailContainerResponse>>

    //Staff
    @POST("InquiryMasterStaffByRole")
    fun inquiryMasterStaffByRole(@Body request: InquiryMasterStaffByRoleRequest): Single<InquiryMasterStaffByRoleWrapperResponse<InquiryMasterStaffByRoleContainerResponse>>

    //Util
    @POST("DeleteImage")
    fun deleteImage(@Body request: DeleteImageRequest): Single<DeleteImageWrapperResponse<DeleteImageContainerResponse>>
}