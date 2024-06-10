package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.MyObjects.createNewNotification
import it.walmann.adhdcompanion.MyObjects.reminder


fun reminderSave(context: Context, reminderToSave: reminder) {
    // Save reminder to file
    MainActivity.reminderDB.ReminderDao().insertAll(reminderToSave)

    // Create notification
    createNewNotification(
        context = context,
        title = "ADHD Reminder!",
        content = reminderToSave.reminderNote,
        time = reminderToSave.reminderCalendar.timeInMillis,
        notificationID = reminderToSave.uid.toInt()
    )
}