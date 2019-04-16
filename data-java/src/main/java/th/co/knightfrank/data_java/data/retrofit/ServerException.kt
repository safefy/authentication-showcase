package th.co.knightfrank.data_java.data.retrofit

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

sealed class ServerException(message: String,
                             val url: String? = null,
                             val response: Response<*>? = null,
                             val retrofit: Retrofit? = null,
                             val rootThrowable: Throwable? = null)
    : RuntimeException(message) {

    fun <T : Any> getErrorBodyAs(jClass: Class<T>): T? {
        val errorBody = response?.errorBody()
        if (errorBody == null || retrofit == null) {
            return null
        }

        val converter: Converter<ResponseBody, T> = retrofit.responseBodyConverter(jClass, arrayOf())
        return converter.convert(errorBody)
    }

    fun asErrorBody(): ErrorBody? {
        return getErrorBodyAs(ErrorBody::class.java)
    }
}

class HTTPServerException(message: String,
                          url: String,
                          response: Response<*>?,
                          retrofit: Retrofit)
    : ServerException(message, url, response, retrofit)

class NetworkServerException(exception: IOException)
    : ServerException(message = exception.message.orEmpty(),
        rootThrowable = exception)

class UnexpectedServerException(throwable: Throwable)
    : ServerException(message = throwable.message.orEmpty(),
        rootThrowable = throwable)