package it.walmann.adhdcompanion.MyObjects

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import it.walmann.adhdcompanion.R

class MyNotification() : BroadcastReceiver() {
    // BroadcastReceiver for handling notifications

    // Method called when the broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        val notificationID = intent.getIntExtra("notificationID", 0)


        // Build the notification using NotificationCompat.Builder
        val notification = NotificationCompat.Builder(context, "reminder")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra("titleExtra")) // Set title from intent
            .setContentText(intent.getStringExtra("messageExtra")) // Set content text from intent
            .build()

        // Get the NotificationManager service
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification using the manager
        manager.notify(notificationID, notification)
    }
}
