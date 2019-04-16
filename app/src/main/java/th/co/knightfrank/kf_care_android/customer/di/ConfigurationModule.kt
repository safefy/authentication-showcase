package th.co.knightfrank.kf_care_android.customer.di

import dagger.Module
import dagger.Provides
import th.co.knightfrank.data_java.data.ServerURL

@Module
class ConfigurationModule {
    @Provides
    fun provideBaseURL(): ServerURL {
        //dev
        //return ServerURL(url = "http://203.154.74.42:8006/KFCareWebAPI.svc/")
        //production
        return ServerURL(url = "http://27.254.141.140/KFCareAPI/KFCareWebAPI.svc/")
    }
}