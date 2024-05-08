package it.walmann.adhdcompanion.Handlers.Settings

import android.content.Context
import android.provider.Settings.SettingNotFoundException
import it.walmann.adhdcompanion.Handlers.FileSaveLoad.saveFileToInternalStorage
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.Calendar
import java.util.LinkedHashMap
import java.util.Properties


// TODO Create getSettings, InitSettings, setSettings.
private val SettingsLoc = "settings.txt"


fun initSettings(context: Context) {
    val defaultSettings = mapOf(
        "reminderDbLoc" to "reminder_db",
        "quickReminderTime" to "10",
        "quickReminderTimeUnit" to "${Calendar.MINUTE}",
    )
    saveFileToInternalStorage(context, FileLoc = SettingsLoc, ObjectToSave = defaultSettings)
}


fun loadSetting(context: Context, setting: String): String {
    val settings = loadSettings(context)

    val settingToReturn = settings.get(setting) ?:"error"

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

fun loadSettings(context: Context): LinkedHashMap<String, Any> {
    val fis: FileInputStream = context.openFileInput(SettingsLoc)
    val ois = ObjectInputStream(fis)
    val returningMap = ois.readObject() as LinkedHashMap<String, Any>

    return returningMap


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
