package th.co.knightfrank.data_java.data

import okhttp3.Interceptor
import okhttp3.Response


class ContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val newRequest = chain!!.request()
                .newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .build()
        return chain.proceed(newRequest)
    }
}