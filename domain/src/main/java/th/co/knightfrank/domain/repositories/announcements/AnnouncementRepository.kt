package th.co.knightfrank.domain.repositories.announcements

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.announcements.*
import th.co.knightfrank.data_java.data.responses.announcements.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnouncementRepository
@Inject
constructor(private val api: API) {

    fun getAnnouncementDetail(getAnnouncementDetailRequest: GetAnnouncementDetailRequest): Single<GetAnnouncementDetailWrapperResponse<GetAnnouncementDetailContainerResponse>> {
        return api.getAnnouncementDetail(getAnnouncementDetailRequest)
    }

    fun acceptAnnouncement(acceptAnnouncementRequest: AcceptAnnouncementRequest): Single<AcceptAnnouncementWrapperResponse<AcceptAnnouncementContainerResponse>> {
        return api.acceptAnnouncement(acceptAnnouncementRequest)
    }

    fun searchAnnouncement(searchAnnouncementRequest: SearchAnnouncementRequest): Single<SearchAnnouncementWrapperResponse<SearchAnnouncementContainerResponse>> {
        return api.searchAnnouncement(searchAnnouncementRequest)
    }

    fun createAnnouncement(createAnnouncementRequest: CreateAnnouncementRequest): Single<CreateAnnouncementWrapperResponse<CreateAnnouncementContainerResponse>> {
        return api.createAnnouncement(createAnnouncementRequest)
    }

    fun editAnnouncement(editAnnouncementRequest: EditAnnouncementRequest): Single<EditAnnouncementWrapperResponse<EditAnnouncementContainerResponse>> {
        return api.editAnnouncement(editAnnouncementRequest)
    }

}