package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import android.net.Uri
import it.walmann.adhdcompanion.Handlers.FileSaveLoad.saveRemindersToInternalFile

import it.walmann.adhdcompanion.Handlers.Settings.loadSetting
import it.walmann.adhdcompanion.MyObjects.createNewNotification
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Calendar


//fun reminderSave(context: Context, reminder: Calendar, reminderImage: Uri = Uri.EMPTY){
fun reminderSave(context: Context, reminderToSave: myReminder) {
    // Save reminder to file
    val curReminders: LinkedHashMap<String, myReminder> = reminderLoad.all(context)

    val newReminder = LinkedHashMap<String, myReminder>()
    newReminder[reminderToSave.reminderKey] = reminderToSave

    curReminders.putAll(newReminder)

    saveRemindersToInternalFile(context, curReminders)

    // Create notification
    createNewNotification(
        context = context,
        title = "ADHD Reminder!",
        content = reminderToSave.reminderNote,
        time = reminderToSave.reminderCalendar.timeInMillis
    )
}