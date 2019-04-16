package th.co.knightfrank.domain.components

interface IAppSettings {

    fun getValue(key: AppSettingsKey): String

    fun getInt(key: AppSettingsKey): Int

    fun getBoolean(key: AppSettingsKey): Boolean

    fun setValue(key: AppSettingsKey, value: String)

    fun setValue(key: AppSettingsKey, value: Int)

    fun setValue(key: AppSettingsKey, value: Boolean)

    fun clearValue(key: AppSettingsKey)
}