package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

//import java.util.prefs.Preferences

//private val SettingsLoc = "settings.txt"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

//fun fixSettingsTypes(settingMap: LinkedHashMap<String, String>): Map<String, Any> {
//
//    val defaultSettings = mapOf(
//        "reminderDbLoc" to settingMap["reminderDbLoc"].toString(),
//        "quickReminderTime" to (settingMap["quickReminderTime"]?.toInt() ?: 0),
//        "quickReminderTimeUnit" to (settingMap["quickReminderTimeUnit"]?.toInt()
//            ?: Calendar.MINUTE),
//    )
//
//    return defaultSettings
//}


suspend fun initSettings(context: Context) {
    val reminderDbLoc = stringPreferencesKey("reminderDbLoc")
    val quickReminderTime = intPreferencesKey("quickReminderTime")
    val quickReminderTimeUnit = intPreferencesKey("quickReminderTimeUnit")

//    val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_preferences")
    context.dataStore.edit { settings ->
        settings[reminderDbLoc] = "reminder_db" //currTempLocation
        settings[quickReminderTime] = 10 //currTempLocation
        settings[quickReminderTimeUnit] = Calendar.MINUTE //currTempLocation
    }

    // Hvordan hente settings:
    // val temp2: Flow<String> = context.dataStore.data.map { preferences ->
    //                preferences[stringPreferencesKey("reminderDbLoc")] ?:""
    //            }
    // val temp3 = temp2.collectAsState(initial = "")
}



fun getReminderDatabaseLocation(context: Context): Flow<String> {
    val temp2: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("reminderDbLoc")] ?: ""
    }
//    val temp3 = temp2.collectAsState(initial = "").value.toString()
    return temp2
}

@Composable
fun getQuickReminderTime(context: Context): Int {
    val temp2: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("quickReminderTime")] ?: ""
    }
    val temp3 = temp2.collectAsState(initial = "").value.toInt()
    return temp3
}

@Composable
fun getQuickReminderTimeUnit(context: Context): Int {
    val temp2: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("quickReminderTimeUnit")] ?: ""
    }
    val temp3 = temp2.collectAsState(initial = "").value.toInt()
    return temp3
}
