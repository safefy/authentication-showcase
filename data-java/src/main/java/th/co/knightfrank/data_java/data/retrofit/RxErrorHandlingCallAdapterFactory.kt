package th.co.knightfrank.data_java.data.retrofit

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory
private constructor() : CallAdapter.Factory() {

    companion object {
        fun create(): CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val wrappedCallAdapter = original.get(returnType, annotations, retrofit) ?: return null
        return RxCallAdapterWrapper(retrofit, wrappedCallAdapter as CallAdapter<Any, Any>)
    }

    private class RxCallAdapterWrapper(val retrofit: Retrofit,
                                       val wrappedCallAdapter: CallAdapter<Any, Any>) : CallAdapter<Any, Any> {
        override fun responseType(): Type = wrappedCallAdapter.responseType()

        override fun adapt(call: Call<Any>): Any {
            return (wrappedCallAdapter.adapt(call).let {
                when (it) {
                    is Single<*> -> it.onErrorResumeNext { error -> Single.error(asServerException(error)) }
                    is Maybe<*> -> it.onErrorResumeNext { error: Throwable -> Maybe.error(asServerException(error)) }
                    is Observable<*> -> it.onErrorResumeNext({ error: Throwable ->
                        Observable.error(
                                asServerException(
                                        error))
                    })
                    else -> (it as Observable<*>).onErrorResumeNext({ error: Throwable ->
                        Observable.error(
                                asServerException(
                                        error))
                    })
                }
            })

        }

        private fun asServerException(throwable: Throwable): ServerException = when (throwable) {
            is HttpException -> {
                val response = throwable.response()
                HTTPServerException(message = "${response.code()} - ${response.message()})",
                        url = response.raw().request().url().toString(),
                        retrofit = retrofit,
                        response = response)
            }
            is IOException -> NetworkServerException(throwable)
            else -> UnexpectedServerException(throwable)
        }

    }
}