package it.walmann.adhdcompanion.myObjects

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime


private fun getRandomKey(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..64)
        .map { allowedChars.random() }
        .joinToString("")


}



class myReminder(
//    val alarmTime : LocalDateTime,
    private val reminderStorageFile: String = "reminder_db.txt",


    var reminderTime: String = "00:00", // TODO NEXT Create the needed code to save Reminder time.
//    var reminderDate: String = "31.12.24",
    var reminderImage: Uri = Uri.EMPTY,
    var reminderNote: String = ""
) {
    @Suppress("UNCHECKED_CAST")
    private fun createMap(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        val currentDateTime = LocalDateTime.now()

        val currMap =mapOf(
            "reminderKey" to getRandomKey(),
            "reminderTime" to "${currentDateTime.hour}:${currentDateTime.minute}",
            "reminderDate" to "${currentDateTime.year}:${currentDateTime.month}${currentDateTime.dayOfMonth}",
            "reminderImage" to reminderImage.lastPathSegment,
            "reminderImageFullPath" to reminderImage.toString(),
            "reminderNote" to reminderNote
        )
        val returningMap = LinkedHashMap<String, LinkedHashMap<String, String>>()
        returningMap[currMap["reminderKey"].toString()] = currMap as LinkedHashMap<String, String>
        return returningMap
    }

    fun saveNewReminder(context: Context) {
        try {
            val curReminders: LinkedHashMap<String, LinkedHashMap<String, String>> = loadReminders(context)
            val reminderToSave = this.createMap()
            curReminders.putAll(reminderToSave)
            val fos: FileOutputStream =
                context.openFileOutput(reminderStorageFile, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(curReminders)
            oos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        message.value = ""
        Toast.makeText(context, "Data saved successfully..", Toast.LENGTH_SHORT).show()
    }

    //    fun loadReminders(context: Context): Map<String, String> {
    @Suppress("UNCHECKED_CAST")
    fun loadReminders(context: Context): LinkedHashMap<String, LinkedHashMap<String, String>> {
        try { //TODO NEXT Make it load the existing ReminderDB and add the new one. Then save it to file.
            val returningMap: LinkedHashMap<String, LinkedHashMap<String, String>>

//            val file = File(context.filesDir, reminderStorageFile)

            val fis: FileInputStream = context.openFileInput(reminderStorageFile)
            val ois = ObjectInputStream(fis)

            returningMap = ois.readObject() as LinkedHashMap<String, LinkedHashMap<String, String>>


            return returningMap

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return LinkedHashMap()
    }

}