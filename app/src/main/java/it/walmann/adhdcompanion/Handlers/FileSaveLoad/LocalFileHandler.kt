package it.walmann.adhdcompanion.Handlers.FileSaveLoad

import android.content.Context
import androidx.datastore.core.DataStore
import com.google.gson.Gson
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.util.prefs.Preferences


fun saveRemindersToInternalFile(context: Context, ObjectToSave: LinkedHashMap<String, myReminder>) {
//    val fileLoc = loadSetting(context, "reminderDbLoc").toString()
    val fileLoc = "reminder_db"

//    val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_preferences")

//    val myReminderToString = ObjectToSave
//    val thingson = Gson().toJson(ObjectToSave)
//
//    val thingsDecoded = Gson(). // TODO NEXT Make settings and reminders available to save onto disk.
//    val temp1 = ""
////    saveFileToInternalStorage(context = context, FileLoc = loadSetting(context, "reminderDbLoc").toString(), ObjectToSave = toMap<String, String>())

}


fun saveFileToInternalStorage(
    context: Context,
    FileLoc: String,
    ObjectToSave: Map<String, String>
) {
    // TODO NEXT Create function for saving settings, and saving reminders.
//    val temp22 = ObjectToSave::class.simpleName
//
//
//    val temp = ObjectToSave
//    val temp2 = ObjectToSave.toString()
//    val temp4 = Json.encodeToString(ObjectToSave)
////            val temp4 = decodeFromString(temp2)
//    val temp3 = ""

    val fos: FileOutputStream =
        context.openFileOutput(FileLoc, Context.MODE_PRIVATE)
    val oos = ObjectOutputStream(fos)
    oos.writeObject(ObjectToSave)
    oos.close()
}