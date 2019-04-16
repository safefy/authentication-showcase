package th.co.knightfrank.kf_care_android.customer.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.data.SchedulerTypes
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Singleton
    @Provides
    @Named(SchedulerTypes.IO)
    fun provideBackgroundScheduler(): Scheduler = Schedulers.io()
}