package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

class Reminder(
//    val alarmTime : LocalDateTime,
    val reminderStorageFile: String = "reminder_db.txt",


    var reminderTime: String = "00:00",
    var reminderDate: String = "31.12.24",
    var reminderImage: Uri = Uri.EMPTY,
    var reminderNote: String = "Lorem Ipsum is simply dummy"
) {
    private fun createArray(): Map<String, Comparable<*>>
    {
//        val currArray =  mapOf("reminderTime" to reminderTime, "reminderDate" to reminderDate, "reminderImage" to reminderImage, "reminderNote" to reminderNote)
        return mapOf(
            "reminderTime" to reminderTime,
            "reminderDate" to reminderDate,
            "reminderImage" to reminderImage,
            "reminderNote" to reminderNote
        )
    }

    fun saveNewReminder(context: Context) {
        try { //TODO NEXT Make it load the existing ReminderDB and add the new one. Then save it to file.
            val reminderToSave = this.createArray()
            val fos: FileOutputStream =
                context.openFileOutput("reminderStorageFile", Context.MODE_PRIVATE)
            fos.write(reminderToSave.toString().toByteArray())
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        message.value = ""
        Toast.makeText(context, "Data saved successfully..", Toast.LENGTH_SHORT).show()
    }
}