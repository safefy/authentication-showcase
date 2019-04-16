package th.co.knightfrank.kf_care_android.customer.di

import dagger.Binds
import dagger.Module
import th.co.knightfrank.domain.components.IAppSettings
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings

@Module
abstract class DataModule {

    @Binds
    abstract fun bindAppSettings(appSettings: AppSettings): IAppSettings
}
