package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import android.provider.Settings.SettingNotFoundException
import it.walmann.adhdcompanion.Handlers.FileSaveLoad.saveFileToInternalStorage
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.Calendar
import java.util.LinkedHashMap


// TODO Create getSettings, InitSettings, setSettings.
private val SettingsLoc = "settings.txt"


fun fixSettingsTypes(settingMap:LinkedHashMap<String, String>): Map<String, Any> {

    val defaultSettings = mapOf(
        "reminderDbLoc" to settingMap["reminderDbLoc"].toString(),
        "quickReminderTime" to (settingMap["quickReminderTime"]?.toInt() ?: 0),
        "quickReminderTimeUnit" to (settingMap["quickReminderTimeUnit"]?.toInt() ?: Calendar.MINUTE),
    )

    return defaultSettings
}


fun initSettings(context: Context) {
    val defaultSettings = mapOf(
        "reminderDbLoc" to "reminder_db",
        "quickReminderTime" to 10.toString(),
        "quickReminderTimeUnit" to Calendar.MINUTE.toString(),
    )
    saveFileToInternalStorage(context, FileLoc = SettingsLoc, ObjectToSave = defaultSettings)
}


fun loadSetting(context: Context, setting: String): Any {
    val settings = loadSettings(context)

    val settingToReturn = settings[setting] ?:"error"

    if (settingToReturn == "error") {
        throw SettingNotFoundException("Setting not found!: ${setting}")
    }

    return settingToReturn //TODO ERROR HERE
}

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

fun loadSettings(context: Context): Map<String, Any> {
    val fis: FileInputStream = context.openFileInput(SettingsLoc)
    val ois = ObjectInputStream(fis)
    val rawMap = ois.readObject() as LinkedHashMap<String, String>
    val fixedMap = fixSettingsTypes(rawMap)
    return fixedMap


//    val props = Properties()
//    context.openFileInput(SettingsLoc).use { stream ->
//        props.load(stream)
//    } // TODO FIX NEXT Something wrong with the encoding? The saved settings is all weird.
//
//
//    return props
//        .map { (key, value) -> key.toString() to value.toString() }
//        .toMap()



}
