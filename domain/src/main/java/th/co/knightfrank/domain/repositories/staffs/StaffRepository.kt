package th.co.knightfrank.domain.repositories.staffs

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.staffs.InquiryMasterStaffByRoleRequest
import th.co.knightfrank.data_java.data.responses.staffs.InquiryMasterStaffByRoleContainerResponse
import th.co.knightfrank.data_java.data.responses.staffs.InquiryMasterStaffByRoleWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StaffRepository
@Inject
constructor(private val api: API) {

    fun inquiryMasterStaffByRole(inquiryMasterStaffByRoleRequest: InquiryMasterStaffByRoleRequest): Single<InquiryMasterStaffByRoleWrapperResponse<InquiryMasterStaffByRoleContainerResponse>> {
        return api.inquiryMasterStaffByRole(inquiryMasterStaffByRoleRequest)
    }

}