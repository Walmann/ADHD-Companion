package it.walmann.adhdcompanion.MyObjects

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermissionExactAlarm
//import it.walmann.adhdcompanion.requestPermissionNotifications
import okhttp3.internal.notify
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date
import java.util.Random


// Constants for notification
//const val notificationID = 121
//const val channelID = "channel1"
//const val titleExtra = "titleExtra"
//const val messageExtra = "messageExtra"


fun createNewNotification(
    context: Context,
    title: String,
    content: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    notificationID: Int = kotlin.random.Random.nextInt(),
//    icon: Int = R.drawable.ic_launcher_foreground,
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


    // Handle permission
//    requestPermissionExactAlarm(context = context, alarmManager)


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

//    showAlert(time, title, content, context)

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