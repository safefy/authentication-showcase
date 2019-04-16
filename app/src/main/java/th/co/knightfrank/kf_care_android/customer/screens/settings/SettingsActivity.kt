package th.co.knightfrank.kf_care_android.customer.screens.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import java.util.*
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, SettingsActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (!appSettings.getValue(AppSettingsKey.LANGUAGE).isNullOrEmpty()) {

            Log.e("SettingsActivity", "Langugage : " + appSettings.getValue(AppSettingsKey.LANGUAGE))

            when (appSettings.getValue(AppSettingsKey.LANGUAGE)) {
                "th" -> {
                    radio_btn_language_th.isChecked = true
                }
                "en" -> {
                    radio_btn_language_en.isChecked = true
                }
            }
        } else {
            radio_btn_language_default.isChecked = true
        }

        var language: String? = ""
        radio_group_language.setOnCheckedChangeListener { group, i ->
            when (i) {
                radio_btn_language_default.id -> {
                    language = ""
                }
                radio_btn_language_th.id -> {
                    language = "th"
                }
                radio_btn_language_en.id -> {
                    language = "en"
                }
            }
        }

        btn_settings_submit.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(resources.getString(R.string.settings_submit_btn_confirm_dialog_title))
                    .setPositiveButton(resources.getString(R.string.settings_submit_btn_confirm_dialog_accept), { dialog, _ ->
                        dialog.dismiss()

                        var config = resources.configuration
                        when (language) {
                            "th" -> {
                                config.setLocale(Locale("th", ""))
                                resources.updateConfiguration(config, null)
                                appSettings.setValue(AppSettingsKey.LANGUAGE, config.locale.language)
                            }
                            "en" -> {
                                config.setLocale(Locale.ENGLISH)
                                resources.updateConfiguration(config, null)
                                appSettings.setValue(AppSettingsKey.LANGUAGE, config.locale.language)
                            }
                            else -> {
                                config.setLocale(Locale.getDefault())
                                resources.updateConfiguration(config, null)
                                appSettings.setValue(AppSettingsKey.LANGUAGE, "")
                            }
                        }

                        SplashActivity.start(this, null)
                    })
                    .setNegativeButton(resources.getString(R.string.settings_submit_btn_confirm_dialog_cancel), { dialog, _ ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }
    }
}
