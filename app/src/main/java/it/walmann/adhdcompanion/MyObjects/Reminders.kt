package it.walmann.adhdcompanion.MyObjects

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.requestPermission
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Calendar
import kotlin.random.Random


private fun getRandomKey(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..64)
        .map { allowedChars.random() }
        .joinToString("")


}


@Suppress("UNCHECKED_CAST")
class myReminder(
//    val alarmTime : LocalDateTime,
    private val reminderStorageFile: String = "reminder_db.txt",


    var reminderCalendar: Calendar = Calendar.getInstance(),
//    var reminderDate: String = "31.12.24",
    var reminderImage: Uri = Uri.EMPTY,
    var reminderNote: String = ""
) {

    private fun createMap(newReminder: Calendar): LinkedHashMap<String, LinkedHashMap<String, Any>> {
        val currentDateTime = Calendar.getInstance()
        val currMap = mapOf(
//            "reminderKey" to getRandomKey(),
            "reminderKey" to "${newReminder.timeInMillis}",
            "reminderCalendar" to "${newReminder.timeInMillis}",
            "reminderCreationCalendar" to "${Calendar.getInstance().timeInMillis}",
            "reminderImage" to reminderImage.lastPathSegment,
            "reminderImageFullPath" to reminderImage.toString(),
            "reminderNote" to reminderNote
        )
        val returningMap = LinkedHashMap<String, LinkedHashMap<String, Any>>()
        returningMap[currMap["reminderKey"].toString()] = currMap as LinkedHashMap<String, Any>
        return returningMap
    }


    fun SaveNewReminder(context: Context, reminderTime: Calendar) {
//        try { // I have commented out the Try block. Is this really needed when writing to internal app storage?
        // Save reminder to file
        val curReminders: LinkedHashMap<String, LinkedHashMap<String, Any>> =
            loadReminders(context)
        val reminderToSave = this.createMap(reminderTime)
        curReminders.putAll(reminderToSave)
        val fos: FileOutputStream =
            context.openFileOutput(reminderStorageFile, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(curReminders)
        oos.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }


        // Create notification
        createScheduledNotification(
            context,
            reminderTime = reminderTime
        )
        print("")

//        message.value = ""
//        Toast.makeText(context, "Data saved successfully..", Toast.LENGTH_SHORT).show()

    }

    //    fun loadReminders(context: Context): Map<String, String> {
    @Suppress("UNCHECKED_CAST")
    fun loadReminders(
        context: Context,
        sort: String = "default"
    ): LinkedHashMap<String, LinkedHashMap<String, Any>> {
        try {
            var returningMap: LinkedHashMap<String, LinkedHashMap<String, Any>>
            val fis: FileInputStream = context.openFileInput(reminderStorageFile)
            val ois = ObjectInputStream(fis)
            returningMap = ois.readObject() as LinkedHashMap<String, LinkedHashMap<String, Any>>

            val temp1 = returningMap.toList().sortedBy { it.first }
            val result = linkedMapOf(*temp1.toTypedArray())

            return result
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return LinkedHashMap()
    }

}


fun getReminderTime(currReminder: LinkedHashMap<String, String>): String {
    return "${
        currReminder["reminderHour"]?.padStart(
            2,
            '0'
        )
    }:${currReminder["reminderMinute"]?.padStart(2, '0')}"
}

fun getReminderDate(currReminder: LinkedHashMap<String, String>): String {
    return "${currReminder["reminderYear"]} - ${
        currReminder["reminderMonth"]?.padStart(
            2,
            '0'
        )
    } - ${currReminder["reminderDay"]?.padStart(2, '0')}"
}
