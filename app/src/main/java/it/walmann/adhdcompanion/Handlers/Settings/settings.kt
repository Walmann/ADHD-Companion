package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import android.provider.Settings.SettingNotFoundException
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
//import androidx.datastore.core.DataStore
import it.walmann.adhdcompanion.Handlers.FileSaveLoad.saveFileToInternalStorage
import it.walmann.adhdcompanion.MyObjects.myReminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.util.Calendar
import java.util.LinkedHashMap

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

//
//fun loadSetting(context: Context, setting: String): Any {
//
//
//
//
//    val settings = loadSettings(context)
//
//
////    val temp1 = context.dataStore.data.map { preferences ->
////        // No type safety.
////        preferences
////    }
////
////    val temp2 = context.dataStore.data.map { preferences -> preferences["reminderDbLoc"] }
//
//    val settingToReturn = settings[setting] ?: "error"
//
//    if (settingToReturn == "error") {
//        throw SettingNotFoundException("Setting not found!: ${setting}")
//    }
//
//    return settingToReturn //TODO ERROR HERE
//}

//fun saveSettings(context: Context, reminder: myReminder){
//
//    context.openFileOutput(SettingsLoc, Context.MODE_PRIVATE).use { stream ->
//        props.load(stream)
//    } // TODO FIX NEXT Something wrong with the encoding? The saved settings is all weird.
//
//
//    return props
//        .map { (key, value) -> key.toString() to value.toString() }
//        .toMap()
//
//    saveFileToInternalStorage(context, FileLoc = SettingsLoc, ObjectToSave = defaultSettings)
//}

//fun loadSettings(context: Context): Map<String, Any> {
//    val fis: FileInputStream = context.openFileInput(SettingsLoc)
//    val ois = ObjectInputStream(fis)
//    val rawMap = ois.readObject() as LinkedHashMap<String, String>
//    val fixedMap = fixSettingsTypes(rawMap)
//    return fixedMap
//
//
////    val props = Properties()
////    context.openFileInput(SettingsLoc).use { stream ->
////        props.load(stream)
////    } // TODO FIX NEXT Something wrong with the encoding? The saved settings is all weird.
////
////
////    return props
////        .map { (key, value) -> key.toString() to value.toString() }
////        .toMap()
//
//
//}
