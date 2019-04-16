package th.co.knightfrank.domain.repositories.utils

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.DeleteImageRequest
import th.co.knightfrank.data_java.data.responses.utils.DeleteImageContainerResponse
import th.co.knightfrank.data_java.data.responses.utils.DeleteImageWrapperResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilRepository
@Inject
constructor(private val api: API) {

    fun delete(deleteImageRequest: DeleteImageRequest): Single<DeleteImageWrapperResponse<DeleteImageContainerResponse>> {
        return api.deleteImage(deleteImageRequest)
    }

}