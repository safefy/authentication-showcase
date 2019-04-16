package th.co.knightfrank.kf_care_android.customer.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import th.co.knightfrank.data_java.data.ContentTypeInterceptor
import th.co.knightfrank.data_java.data.ServerURL
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.jsonAdapters.LocalDateAdapter
import th.co.knightfrank.data_java.data.jsonAdapters.LocalDateTimeAdapter
import th.co.knightfrank.data_java.data.jsonAdapters.OffsetDateTimeAdapter
import th.co.knightfrank.kf_care_android.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
class APIModule {

    companion object {
        const val AUTHENTICATED_OK_HTTP = "AUTHENTICATED_OK_HTTP"
        const val NORMAL_OK_HTTP = "NORMAL_OK_HTTP"
    }

    @Singleton
    @Provides
    fun providerKFCareAPI(baseURL: ServerURL,
                          moshi: Moshi,
                          @Named(NORMAL_OK_HTTP) okHttpClient: OkHttpClient): API {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL.url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
        return retrofit.create(API::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideMoshi(resourceFactory: JsonAdapter.Factory): Moshi {
//        val moshi = Moshi.Builder()
//                .add(resourceFactory)
//                .add(LocalDateAdapter())
//                .add(OffsetDateTimeAdapter())
//                .add(LocalDateTimeAdapter())
//                .build()
//        return moshi
//    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        val moshi = Moshi.Builder()
                .add(LocalDateAdapter())
                .add(OffsetDateTimeAdapter())
                .add(LocalDateTimeAdapter())
                .build()
        return moshi
    }

    @Singleton
    @Provides
    @Named(NORMAL_OK_HTTP)
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(ContentTypeInterceptor())
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        return builder.build()
    }

}