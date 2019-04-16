package th.co.knightfrank.domain.repositories.dashboards

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardListRequest
import th.co.knightfrank.data_java.data.requests.dashboards.InquiryDashboardRequest
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardContainerResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardListContainerResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardListWrapperResponse
import th.co.knightfrank.data_java.data.responses.dashboards.InquiryDashboardWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository
@Inject
constructor(private val api: API) {

    fun inquiryDashboard(inquiryDashboardRequest: InquiryDashboardRequest): Single<InquiryDashboardWrapperResponse<InquiryDashboardContainerResponse>> {
        return api.inquiryDashboard(inquiryDashboardRequest)
    }

    fun inquiryDashboardList(inquiryDashboardListRequest: InquiryDashboardListRequest): Single<InquiryDashboardListWrapperResponse<InquiryDashboardListContainerResponse>> {
        return api.inquiryDashboardList(inquiryDashboardListRequest)
    }

}