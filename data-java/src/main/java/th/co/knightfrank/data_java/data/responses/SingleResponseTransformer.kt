package th.co.knightfrank.data_java.data.responses

import io.reactivex.Single
import io.reactivex.SingleTransformer
import th.co.knightfrank.data_java.data.exceptions.APIException

class SingleResponseTransformer<D> : SingleTransformer<ResponseEntity<D>, D> {
    override fun apply(upstream: Single<ResponseEntity<D>>): Single<D> {
        return upstream.map { (status, data) ->
            if (/*status == ResponseStatus || */ data == null) {
                throw APIException(
                        status.toString()
//                        message.toString(), statusCode
                )
            }
            data!!
        }
    }
}