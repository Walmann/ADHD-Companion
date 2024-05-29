package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.Calendar


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")


suspend fun getAppSetting(context: Context, setting: String): String? {
    val dataStoreKey = stringPreferencesKey(setting)
    val preferences = context.dataStore.data.first()
    val preferencesDefaults: Map<String, String> =
        mapOf("quickReminderTime" to "10", "quickReminderTimeUnit" to Calendar.MINUTE.toString())

     val temp = if (preferences[dataStoreKey] != null) {
        preferences[dataStoreKey].toString()
    } else {
        preferencesDefaults[dataStoreKey.toString()] // previous: null
    }
    return temp
}

class AppSettings(context: Context, setting: String) {
    companion object {
        @Stable
        val QuickReminderUnit = "quickReminderTimeUnit"

        @Stable
        val QuickReminderValue = "quickReminderTime"
    }
}

suspend fun setAppSetting(context: Context, setting: String, value: Int) {
    val settingToSave = intPreferencesKey(setting)

    context.dataStore.edit { settings ->
        settings[settingToSave] = value
//        settings[quickReminderTimeUnit] = Calendar.MINUTE //currTempLocation
    }
}




