package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import android.net.Uri
import java.util.Calendar

fun CreateNewReminder(context: Context, reminder: Calendar, reminderImage: Uri = Uri.EMPTY, reminderNote: String = ""): LinkedHashMap<String, LinkedHashMap<String, Any>> {
    val currentDateTime = Calendar.getInstance()
    val currMap = mapOf(
//            "reminderKey" to getRandomKey(),
        "reminderKey" to "${reminder.timeInMillis}",
        "reminderCalendar" to "${reminder.timeInMillis}",
        "reminderCreationCalendar" to "${Calendar.getInstance().timeInMillis}",
        "reminderImage" to reminderImage.lastPathSegment,
        "reminderImageFullPath" to reminderImage.toString(),
        "reminderNote" to reminderNote
    )
    val returningMap = LinkedHashMap<String, LinkedHashMap<String, Any>>()
    returningMap[currMap["reminderKey"].toString()] = currMap as LinkedHashMap<String, Any>
    return returningMap
}