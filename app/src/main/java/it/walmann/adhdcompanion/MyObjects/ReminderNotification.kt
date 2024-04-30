package it.walmann.adhdcompanion.MyObjects

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.R
import okhttp3.internal.notify
import java.util.Calendar



// Constants for notification
const val notificationID = 121
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"


// BroadcastReceiver for handling notifications
class Notification : BroadcastReceiver() {

    // Method called when the broadcast is received
    override fun onReceive(context: Context, intent: Intent) {

        // Build the notification using NotificationCompat.Builder
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra)) // Set title from intent
            .setContentText(intent.getStringExtra(messageExtra)) // Set content text from intent
            .build()

        // Get the NotificationManager service
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification using the manager
        manager.notify(notificationID, notification)
    }
}

fun createNewNotification(){ // TODO NEXT
    // https://www.geeksforgeeks.org/schedule-notifications-in-android/
}


private fun createNotificationChannel(context: Context) {
    // Create a notification channel for devices running
    // Android Oreo (API level 26) and above
    val name = "Reminders"
    val desc = "Reminders set with the app"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelID, name, importance)
    channel.description = desc

    // Get the NotificationManager service and create the channel
    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
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