package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import it.walmann.adhdcompanion.readAllKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.Calendar

//import java.util.prefs.Preferences

//private val SettingsLoc = "settings.txt"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

//suspend fun initSettings(context: Context) {
//    val preferencesDefaults = mapOf("quickReminderTime" to "10", "quickReminderTimeUnit" to Calendar.MINUTE)
//    return if (preferences[dataStoreKey] != null) {
//        preferences[dataStoreKey]
//    } else {
//        preferencesDefaults[dataStoreKey] // previous: null
//    }
//
//
////    val quickReminderTime = intPreferencesKey("quickReminderTime")
////    val quickReminderTimeUnit = intPreferencesKey("quickReminderTimeUnit")
////    val hasSettingsBeenInitialized = booleanPreferencesKey("isInitialized")
////
////    val temp = context.dataStoreFile(context)
////
////    context.dataStore.edit { settings ->
////        if (settings[hasSettingsBeenInitialized] == false) {
////            settings[quickReminderTime] = 10 //currTempLocation
////            settings[quickReminderTimeUnit] = Calendar.MINUTE //currTempLocation
////            settings[hasSettingsBeenInitialized] = true
////        }
////    }
//
//    // Hvordan hente settings:
//    // val temp2: Flow<String> = context.dataStore.data.map { preferences ->
//    //                preferences[stringPreferencesKey("reminderDbLoc")] ?:""
//    //            }
//    // val temp3 = temp2.collectAsState(initial = "")
//}


//@Composable
//fun getQuickReminderTime(context: Context): Int {
//    val temp2: Flow<String> = context.dataStore.data.map { preferences ->
//        preferences[stringPreferencesKey("quickReminderTime")] ?: ""
//    }
//    val temp3 = temp2.collectAsState(initial = "").value.toInt()
//    return temp3
//}
//
//@Composable
//fun getQuickReminderTimeUnit(context: Context): Int {
//    val temp2: Flow<String> = context.dataStore.data.map { preferences ->
//        preferences[stringPreferencesKey("quickReminderTimeUnit")] ?: ""
//    }
//    val temp3 = temp2.collectAsState(initial = "").value.toInt()
//    return temp3
//}

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

//    val ds = context.readAllKeys()
//    val ds2 = ds
//    return context.dataStore.data.map { preferences ->
//        preferences[stringPreferencesKey(setting)] ?: ""
//    }

}

class AppSettings(context: Context, setting: String) {
    companion object {
        @Stable
        val QuickReminderUnit = "quickReminderTimeUnit"

        @Stable
        val QuickReminderValue = "quickReminderTime"

//        @Stable
//        val isInitialized = "isInitialized"

    }
}

suspend fun setAppSetting(context: Context, setting: String, value: Int) {
    val settingToSave = intPreferencesKey(setting)

    context.dataStore.edit { settings ->
        settings[settingToSave] = value
//        settings[quickReminderTimeUnit] = Calendar.MINUTE //currTempLocation
    }
}




