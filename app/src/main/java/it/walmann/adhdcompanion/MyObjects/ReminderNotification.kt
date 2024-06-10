package it.walmann.adhdcompanion.MyObjects


import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import it.walmann.adhdcompanion.R
import java.util.Date


private fun getNewReminderIntent(context: Context, title: String = "ADHD Reminder!", reminder: reminder, notificationID: Int): Intent {
    val intent = Intent(context, MyNotification::class.java)

    // Add title and message as extras to the intent
    intent.putExtra("titleExtra", title)
    intent.putExtra("messageExtra", reminder.reminderNote)
    intent.putExtra("notificationID", notificationID)
    intent.putExtra("reminderUID", reminder.uid.toString())

    return intent
}

private fun getNewReminderPendingIntent(context: Context, title: String, reminder: reminder, notificationID: Int, intent: Intent): PendingIntent {
    return PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

}

fun createNewNotification(
    context: Context,
    title: String,
    reminder: reminder,
    icon: Int = R.mipmap.ic_launcher_foreground,

    ) {
    val notificationID = reminder.uid.toInt()
    val intent = getNewReminderIntent(context, title, reminder, notificationID)
    val pendingIntent = getNewReminderPendingIntent(context, title, reminder, notificationID, intent)

    // Get the Alarm Service
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager



    try {
        // Try to create the Alarm for notification.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminder.reminderCalendar.timeInMillis,
            pendingIntent
        )
    } catch (e: SecurityException) {
        Log.e("ExactAlarm", "Error with Alarm Permission!")
    }

}


fun createNotificationChannel(context: Context) {
    // Create a notification channel for devices running
    // Android Oreo (API level 26) and above
    val name = "Reminder notifications"
    val desc = "Reminders set in the app"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel("reminder", name, importance)
    channel.description = desc

    // Get the NotificationManager service and create the channel
    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

private fun showAlert(time: Long, title: String, message: String, context: Context) {
    // Format the time for display
    val date = Date(time)
    val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
    val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

    // Create and show an alert dialog with notification details
    AlertDialog.Builder(context)
        .setTitle("Notification Scheduled")
        .setMessage(
            "Title: $title\nMessage: $message\nAt: ${dateFormat.format(date)} ${
                timeFormat.format(
                    date
                )
            }"
        )
        .setPositiveButton("Okay") { _, _ -> }
        .show()
}


fun deleteNotification(
    context: Context,
    title: String = "ADHD Reminder!",
    reminder: reminder,
    ) {
        val notificationID = reminder.uid.toInt()
        val intent = getNewReminderIntent(context, title, reminder, notificationID)
        val pendingIntent = getNewReminderPendingIntent(context, title, reminder, notificationID, intent)

        // Get the Alarm Service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        try {
            // Try to delete the notification from the future.
            alarmManager.cancel(
                pendingIntent,
            )
        } catch (e: SecurityException) {
            Log.e("ExactAlarm", "Error with Alarm Permission!")
        }

    }