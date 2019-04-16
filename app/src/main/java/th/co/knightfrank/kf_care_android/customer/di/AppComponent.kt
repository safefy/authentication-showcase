package th.co.knightfrank.kf_care_android.customer.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import th.co.knightfrank.kf_care_android.MainApplication
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        APIModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ViewModelModule::class,
        ConfigurationModule::class,
        DataModule::class,
        SchedulersModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(mainApplication: MainApplication): Builder

        fun build(): AppComponent
    }

    fun inject(mainApplication: MainApplication)
}