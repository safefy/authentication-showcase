package th.co.knightfrank.kf_care_android.customer.ui.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.alertfacade.IAlertFacade
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var alertFacade: IAlertFacade

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Log.e("appSettings", "Language : " + AppSettings(this).getValue(AppSettingsKey.LANGUAGE))

        val config = resources.configuration
        if (AppSettings(this).getValue(AppSettingsKey.LANGUAGE).isNotEmpty()) {
            config.setLocale(Locale(AppSettings(this).getValue(AppSettingsKey.LANGUAGE)))
        } else {
            config.setLocale(Locale.getDefault())
        }

        resources.updateConfiguration(config, resources.displayMetrics)

        setContentView(getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    protected fun hideSoftKeyboard(activity: Activity?) {
        activity?.run {
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
