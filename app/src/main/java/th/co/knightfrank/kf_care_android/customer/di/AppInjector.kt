package th.co.knightfrank.kf_care_android.customer.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import th.co.knightfrank.kf_care_android.MainApplication
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity

class AppInjector {

    constructor(mainApplication: MainApplication) {
        DaggerAppComponent.builder().application(mainApplication).build().inject(mainApplication)

        mainApplication.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }
        })
    }

    fun handleActivity(activity: Activity?) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is BaseActivity) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(fm: FragmentManager, f: Fragment,
                                                               savedInstanceState: Bundle?) {
                                    if (f is Injectable) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true)
        }
    }
}