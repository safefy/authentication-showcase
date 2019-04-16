package th.co.knightfrank.domain.repositories.messages


import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.messages.CreateInboxMessageRequest
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageByUserIDRequest
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageDetailRequest
import th.co.knightfrank.data_java.data.requests.messages.ReplyInboxMesssageRequest
import th.co.knightfrank.data_java.data.responses.messages.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository
@Inject
constructor(private val api: API) {

    fun createInboxMessage(createInboxMessageRequest: CreateInboxMessageRequest): Single<CreateInboxMessageWrapperResponse<CreateInboxMessageContainerResponse>> {
        return api.createInboxMessage(createInboxMessageRequest)
    }

    fun getInboxMessageByUserID(getInboxMessageByUserIDRequest: GetInboxMessageByUserIDRequest): Single<GetInboxMessageByUserIDWrapperResponse<GetInboxMessageByUserIDContainerResponse>> {
        return api.getInboxMessageByUserID(getInboxMessageByUserIDRequest)
    }

    fun getInboxMessageDetail(getInboxMessageDetailRequest: GetInboxMessageDetailRequest): Single<GetInboxMessageDetailWrapperResponse<GetInboxMessageDetailContainerResponse>> {
        return api.getInboxMessageDetail(getInboxMessageDetailRequest)
    }

    fun replyInboxMessage(replyInboxMessageRequest: ReplyInboxMesssageRequest): Single<ReplyInboxMessageWrapperResponse<ReplyInboxMessageContainerResponse>> {
        return api.replyInboxMessage(replyInboxMessageRequest)
    }

}