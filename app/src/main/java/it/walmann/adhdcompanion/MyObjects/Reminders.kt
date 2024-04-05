package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Reminder(
//    val alarmTime : LocalDateTime,
    val reminderStorageFile: String = "reminder_db.txt",


    var reminderTime: String = "00:00",
    var reminderDate: String = "31.12.24",
    var reminderImage: Uri = Uri.EMPTY,
    var reminderNote: String = "Lorem Ipsum is simply dummy"
) {
    private fun createMap(): Map<String, Comparable<*>> {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val randomKey = allowedChars.random()
//        val currArray =  mapOf("reminderTime" to reminderTime, "reminderDate" to reminderDate, "reminderImage" to reminderImage, "reminderNote" to reminderNote)
        return mapOf(
            "reminderKey" to randomKey,
            "reminderTime" to reminderTime,
            "reminderDate" to reminderDate,
            "reminderImage" to reminderImage.toString(),
            "reminderNote" to reminderNote
        )
    }

    fun saveNewReminder(context: Context) {
        try {
            val curReminders = loadReminders(context)

            val reminderToSave = this.createMap()

//            curReminders.

            val file = File(context.filesDir, reminderStorageFile)
            val data = reminderToSave
//            file.writeText(data + "\n" + data + "\n" + data + "\n" + data + "\n" + data + "\n")

            val fos: FileOutputStream = context.openFileOutput(reminderStorageFile, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(reminderToSave)
            oos.close()
//            fos.write(reminderToSave.toString().toByteArray())
//            fos.flush()
//            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        message.value = ""
        Toast.makeText(context, "Data saved successfully..", Toast.LENGTH_SHORT).show()
    }

    fun loadReminders(context: Context): Map<String, String> {
        val returningMap: LinkedHashMap<String, String>
        try { //TODO NEXT Make it load the existing ReminderDB and add the new one. Then save it to file.
//            val maps = mutableListOf<Map<String, String>>()

            val file = File(context.filesDir, reminderStorageFile)

            val fis: FileInputStream = context.openFileInput(reminderStorageFile)
            val ois = ObjectInputStream(fis)

            returningMap = ois.readObject() as Map<String, String> // TODO NEXT Fix this. Need to make the Object into a map.

            print("")

//            return returningMap.toMap()


        } catch (e: IOException) {
            e.printStackTrace()
        }

//        return returningMap.toMap()
    }
}