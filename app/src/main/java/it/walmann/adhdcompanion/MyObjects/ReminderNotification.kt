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
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermissionExactAlarm
import it.walmann.adhdcompanion.requestPermissionNotifications
import okhttp3.internal.notify
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date


// Constants for notification
const val notificationID = 121
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"



@Composable
fun createNewNotification(
    context: Context,
    title: String,
    content: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    icon: Int = R.drawable.ic_launcher_foreground,
    time: Long = LocalTime.now().getLong(ChronoField.MILLI_OF_DAY)
) { // TODO NEXT
    // https://www.geeksforgeeks.org/schedule-notifications-in-android/

    val intent = Intent(context, MyNotification::class.java)

    // Add title and message as extras to the intent
    intent.putExtra(titleExtra, title)
    intent.putExtra(messageExtra, content)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Get the Alarm Service
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Handle permission


    var hasAlarmPermission = false
    var hasNotificationPermission = false
//    val hasAlarmPermission = remember { mutableStateOf(true) }
//    val hasNotificationPermission = remember { mutableStateOf(true) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        hasAlarmPermission =
            requestPermissionExactAlarm(context=context, alarmManager)
    }


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        hasNotificationPermission = // TODO NEXT Get permission to work.
            requestPermissionNotifications(context=context)
    }
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        if (!alarmManager.canScheduleExactAlarms()) {
//            Intent().also { x ->
//                x.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
//                context.startActivity(x)
//            }
//        }
//    }



    try {
        // Try to create the Alarm for notification.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    } catch (e: SecurityException){
        print("Error with Alarm Permission!")
    }

    showAlert(time, title, content, context)

}


fun createNotificationChannel(context: Context) {
    // Create a notification channel for devices running
    // Android Oreo (API level 26) and above
    val name = "Reminder notifications"
    val desc = "Reminders set in the app"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelID, name, importance)
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
            "Title: $title\nMessage: $message\nAt: ${dateFormat.format(date)} ${timeFormat.format(date)}"
        )
        .setPositiveButton("Okay") { _, _ -> }
        .show()
}





//
//fun newNotification( // TODO Add description on how to use.
//    context: Context,
//    title: String,
//    content: String,
//    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
//    icon: Int = R.drawable.ic_launcher_foreground,
//): Notification {
//    val intent = Intent(context, MainActivity::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//
//    val builder = NotificationCompat.Builder(context, "Reminder_channel")
//        .setSmallIcon(icon)
//        .setContentTitle(title)
//        .setContentText(content)
//        .setPriority(priority)
//        .setContentIntent(pendingIntent)
//        .setAutoCancel(true)
//        .build()
//
//
//
//    val temp2 = builder
//    return temp2
//}
//
//fun ReminderNotification(context: Context, notificationID:Int, builder:android.app.Notification) {
//    val notificationManager = createNotificationManager(context)
//
//
//    if (ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.POST_NOTIFICATIONS
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//
//        // TODO: Consider calling
//        //    ActivityCompat#requestPermissions
//        // here to request the missing permissions, and then overriding
//        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//        //                                          int[] grantResults)
//        // to handle the case where the user grants the permission. See the documentation
//        // for ActivityCompat#requestPermissions for more details.
//        notificationManager.notify(notificationID, builder)
//        return
//    }
//
//}
//
//fun createNotificationManager(context: Context): NotificationManager {
//// https://developer.android.com/develop/ui/views/notifications/channels#CreateChannel
//    // Create the NotificationChannel.
//    val name = "Reminders"
//    val descriptionText = "Reminders created by the user"
//    val importance = NotificationManager.IMPORTANCE_HIGH
//    val mChannel = NotificationChannel("Reminder_channel", name, importance)
//    mChannel.description = descriptionText
//    // Register the channel with the system. You can't change the importance
//    // or other notification behaviors after this.
//    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.createNotificationChannel(mChannel)
//
//    return notificationManager
//}
//
//
//@SuppressLint("ScheduleExactAlarm")
//fun createScheduledNotification(context: Context, reminderTime: Calendar): Unit {
//    val myNotification = newNotification(context = context, title = "New ADHD reminder!", content = "tempString")
////    ReminderNotification(context = context, notificationID = Random.nextInt(), builder= myNotification)
//
//
//    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val alarmIntent = Intent(context, MainActivity::class.java).let { intent ->
//        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//    }
//
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
//    } else {
//        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
//    }
//
////    alarmMgr!!.setExactAndAllowWhileIdle(
////        AlarmManager.RTC_WAKEUP,
////        reminderTime.timeInMillis,
////        alarmIntent
////    )
////    print("")
//
//}