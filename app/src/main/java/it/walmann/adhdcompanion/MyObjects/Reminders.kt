package it.walmann.adhdcompanion.MyObjects

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


    var reminderTime: String = "",
//    var reminderDate: String = "31.12.24",
    var reminderImage: Uri = Uri.EMPTY,
    var reminderNote: String = ""
) {
    @Suppress("UNCHECKED_CAST")
    private fun createMap(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        val currentDateTime = LocalDateTime.now()
        val currMap =mapOf(
            "reminderKey" to getRandomKey(),

            "reminderYear" to "${currentDateTime.year}",
            "reminderMonth" to "${currentDateTime.monthValue}",
            "reminderDay" to "${currentDateTime.dayOfMonth}",
            "reminderHour" to "${currentDateTime.hour}",
            "reminderMinute" to "${currentDateTime.minute}", // TODO Format to make it look better

            "reminderCreationYear" to "${currentDateTime.year}",
            "reminderCreationMonth" to "${currentDateTime.monthValue}",
            "reminderCreationDay" to "${currentDateTime.dayOfMonth}",
            "reminderCreationHour" to "${currentDateTime.hour}",
            "reminderCreationMinute" to "${currentDateTime.minute}",

//            "reminderCreationTime" to "${currentDateTime.hour}:${currentDateTime.minute}",
//            "reminderCreationDate" to "${currentDateTime.year}-${currentDateTime.monthValue}-${currentDateTime.dayOfMonth}",

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
        try {
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


fun getReminderTime(currReminder: LinkedHashMap<String, String>): String {
    return "${currReminder["reminderHour"]?.padStart(2,'0')}:${currReminder["reminderMinute"]?.padStart(2, '0')}"
}fun getReminderDate(currReminder:  LinkedHashMap<String, String>): String {
    return "${currReminder["reminderYear"]} - ${currReminder["reminderMonth"]?.padStart(2, '0')} - ${currReminder["reminderDay"]?.padStart(2, '0')}"
}
