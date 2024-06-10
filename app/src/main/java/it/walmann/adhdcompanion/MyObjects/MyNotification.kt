package it.walmann.adhdcompanion.MyObjects

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.R

class MyNotification() : BroadcastReceiver() {
    // BroadcastReceiver for handling notifications

    // Method called when the broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        val notificationID = intent.getIntExtra("notificationID", 0)

        val tapAction = Intent(context, MainActivity::class.java)

        tapAction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        tapAction.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        tapAction.putExtra("reminderUID", intent.getStringExtra("reminderUID"))

        val pendingTapAction =
            PendingIntent.getActivity(
                context,
                notificationID,
                tapAction,
                PendingIntent.FLAG_IMMUTABLE
            )


        // Build the notification using NotificationCompat.Builder
        val notificationBuilder = NotificationCompat.Builder(context, "reminder")
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra("titleExtra")) // Set title from intent
            .setContentText(intent.getStringExtra("messageExtra")) // Set content text from intent
            .setContentIntent(pendingTapAction)
            .build()

        // Get the NotificationManager service
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification using the manager
        manager.notify(notificationID, notificationBuilder)
    }
}
