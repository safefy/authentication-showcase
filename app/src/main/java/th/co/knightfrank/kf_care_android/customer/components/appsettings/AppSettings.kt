package th.co.knightfrank.kf_care_android.customer.components.appsettings

import android.content.Context
import android.preference.PreferenceManager
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.domain.components.IAppSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettings
@Inject
constructor(val context: Context) : IAppSettings {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getValue(key: AppSettingsKey): String {
        return sharedPreferences.getString(key.key, "")
    }

    override fun getInt(key: AppSettingsKey): Int {
        return sharedPreferences.getInt(key.key, -1)
    }

    override fun getBoolean(key: AppSettingsKey): Boolean {
        return sharedPreferences.getBoolean(key.key, false)
    }

    override fun setValue(key: AppSettingsKey, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key.key, value)
        editor.apply()
    }

    override fun setValue(key: AppSettingsKey, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key.key, value)
        editor.apply()
    }

    override fun setValue(key: AppSettingsKey, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key.key, value)
        editor.apply()
    }

    override fun clearValue(key: AppSettingsKey) {
        val editor = sharedPreferences.edit()
        editor.remove(key.key)
        editor.apply()
    }
}
