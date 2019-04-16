package th.co.knightfrank.kf_care_android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import com.crashlytics.android.Crashlytics
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.fabric.sdk.android.Fabric
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.di.AppInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    companion object {
        private lateinit var _applicationContext: Context

        val applicationContext: Context
            get() = _applicationContext
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var appSettings: AppSettings

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        _applicationContext = this

        AndroidThreeTen.init(this)
        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)
        AppInjector(this)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        } else {
            Fabric.with(this, Crashlytics())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }


}