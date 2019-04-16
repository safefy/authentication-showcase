package th.co.knightfrank.domain.repositories.jobrequests

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.jobrequests.*
import th.co.knightfrank.data_java.data.responses.jobrequests.*
import th.co.knightfrank.data_java.data.responses.jobrequests.search.GetJobRequestListContainerResponse
import th.co.knightfrank.data_java.data.responses.jobrequests.search.GetJobRequestListWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRequestRepository
@Inject
constructor(private val api: API) {

    fun createJobRequest(createJobRequestRequest: CreateJobRequestRequest): Single<CreateJobRequestWrapperResponse<CreateJobRequestContainerResponse>> {
        return api.createJobRequest(createJobRequestRequest)
    }

    fun getJobRequestByStatus(getJobRequestByStatusRequest: GetJobRequestByStatusRequest): Single<GetJobRequestByStatusWrapperResponse<GetJobRequestByStatusContainerResponse>> {
        return api.getJobRequestByStatus(getJobRequestByStatusRequest)
    }

    fun getJobRequestByUserID(getJobRequestByUserIDRequest: GetJobRequestByUserIDRequest): Single<GetJobRequestByUserIDWrapperResponse<GetJobRequestByStatusContainerResponse>> {
        return api.getJobRequestByUserID(getJobRequestByUserIDRequest)
    }

    fun getJobRequestDetail(getJobRequestDetailRequest: GetJobRequestDetailRequest): Single<GetJobRequestDetailWrapperResponse<GetJobRequestDetailContainerResponse>> {
        return api.getJobRequestDetail(getJobRequestDetailRequest)
    }

    fun getJobRequestTransactionList(getJobRequestTransactionListRequest: GetJobRequestTransactionListRequest): Single<GetJobRequestTransactionListWrapperResponse<GetJobRequestTransactionListContainerResponse>> {
        return api.getJobRequestTransactionList(getJobRequestTransactionListRequest)
    }

    fun jobApprove(jobApproveRequest: JobApproveRequest): Single<JobApproveWrapperResponse<JobApproveContainerResponse>> {
        return api.jobApprove(jobApproveRequest)
    }

    fun inquiryMasterJobAreaList(inquiryMasterJobAreaListRequest: InquiryMasterJobAreaListRequest): Single<InquiryMasterJobAreaListWrapperResponse<InquiryMasterJobAreaListContainerResponse>> {
        return api.inquiryMasterJobAreaList(inquiryMasterJobAreaListRequest)
    }

    fun inquiryMasterJobPriorityList(inquiryMasterJobPriorityListRequest: InquiryMasterJobPriorityListRequest): Single<InquiryMasterJobPriorityListWrapperResponse<InquiryMasterJobPriorityListContainerResponse>> {
        return api.inquiryMasterJobPriorityList(inquiryMasterJobPriorityListRequest)
    }

    fun inquiryMasterJobSystemTypeList(inquiryMasterJobSystemTypeListRequest: InquiryMasterJobSystemTypeListRequest): Single<InquiryMasterJobSystemTypeListWrapperResponse<InquiryMasterJobSystemTypeListContainerResponse>> {
        return api.inquiryMasterJobSystemTypeList(inquiryMasterJobSystemTypeListRequest)
    }

    fun createJobRequestByAdmin(createJobRequestByAdminRequest: CreateJobRequestByAdminRequest): Single<CreateJobRequestByAdminWrapperResponse<CreateJobRequestByAdminContainerResponse>> {
        return api.createJobRequestByAdmin(createJobRequestByAdminRequest)
    }

    fun jobAssignment(jobAssignmentRequest: JobAssignmentRequest): Single<JobAssignmentWrapperResponse<JobAssignmentContainerResponse>> {
        return api.jobAssignment(jobAssignmentRequest)
    }

    fun jobAccept(jobAcceptRequest: JobAcceptRequest): Single<JobAcceptWrapperResponse<JobAcceptContainerResponse>> {
        return api.jobAccept(jobAcceptRequest)
    }

    fun jobTakeActionResult(jobTakeActionResultRequest: JobTakeActionResultRequest): Single<JobTakeActionResultWrapperResponse<JobTakeActionResultContainerResponse>> {
        return api.jobTakeActionResult(jobTakeActionResultRequest)
    }

    fun jobApproveByManager(jobApproveByManagerRequest: JobApproveByManagerRequest): Single<JobApproveByManagerWrapperResponse<JobApproveByManagerContainerResponse>> {
        return api.jobApproveByManager(jobApproveByManagerRequest)
    }

    fun getJobRequestList(getJobRequestListRequest: GetJobRequestListRequest): Single<GetJobRequestListWrapperResponse<GetJobRequestListContainerResponse>> {
        return api.getJobRequestList(getJobRequestListRequest)
    }

    fun inquiryMasterJobStatusList(inquiryMasterJobStatusListRequest: InquiryMasterJobStatusListRequest): Single<InquiryMasterJobStatusListWrapperResponse<InquiryMasterJobStatusListContainerResponse>> {
        return api.inquiryMasterJobStatusList(inquiryMasterJobStatusListRequest)
    }

}