package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import android.net.Uri
import it.walmann.adhdcompanion.Handlers.FileSaveLoad.saveRemindersToInternalFile
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.MyObjects.createNewNotification
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.MyObjects.reminder
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Calendar



//fun reminderSave(context: Context, reminderToSave: myReminder) {
fun reminderSave(context: Context, reminderToSave: reminder) {
    // Save reminder to file
//    val curReminders: LinkedHashMap<String, myReminder> = reminderLoad.all(context)
//    val newReminder = LinkedHashMap<String, myReminder>()
//    newReminder[reminderToSave.reminderKey] = reminderToSave
//    curReminders.putAll(newReminder)

    val cal = Calendar.getInstance() //TODO NEXT ReminderImage is empty. Temporary problem, or error in code?
    val newNewReminder = reminder(uid = cal.timeInMillis)

    MainActivity.reminderDB.ReminderDao().insertAll(newNewReminder)


    // Create notification
    createNewNotification(
        context = context,
        title = "ADHD Reminder!",
        content = reminderToSave.reminderNote,
//        time = reminderToSave.reminderCalendar.timeInMillis //TODO("TEMP")
        time = 0
    )
}