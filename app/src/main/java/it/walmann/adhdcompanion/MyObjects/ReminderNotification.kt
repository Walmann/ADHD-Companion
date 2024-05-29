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
import androidx.core.app.NotificationCompat
import it.walmann.adhdcompanion.R
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.util.Date


fun createNewNotification(
    context: Context,
    title: String,
    content: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    notificationID: Int = kotlin.random.Random.nextInt(),
    icon: Int = R.mipmap.ic_launcher_foreground,
    time: Long = LocalTime.now().getLong(ChronoField.MILLI_OF_DAY)
) {
    val intent = Intent(context, MyNotification::class.java)

    // Add title and message as extras to the intent
    intent.putExtra("titleExtra", title)
    intent.putExtra("messageExtra", content)
    intent.putExtra("notificationID", notificationID)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Get the Alarm Service
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager



    try {
        // Try to create the Alarm for notification.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
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