package th.co.knightfrank.data_java.data.responses

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import th.co.knightfrank.data_java.data.Optional
import th.co.knightfrank.data_java.data.exceptions.APIException
import th.co.knightfrank.data_java.data.responses.users.LoginWrapperResponse

class ResponseTransformer<D> : ObservableTransformer<LoginWrapperResponse<D>, Optional<D>> {

    override fun apply(upstream: Observable<LoginWrapperResponse<D>>): ObservableSource<Optional<D>> {
        return upstream.map { (data) ->
            if (data == null) {
                throw APIException("")
            }
            Optional.of(data)
        }
    }
}