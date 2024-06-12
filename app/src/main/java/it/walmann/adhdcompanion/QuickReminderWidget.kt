package it.walmann.adhdcompanion

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class QuickReminderWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {


            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val tapAction = Intent(context, MainActivity::class.java).also {
            intent ->
        intent.putExtra("createNewReminder", true)
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        /* context = */ context,
        /* requestCode = */  0,
        /* intent = */ tapAction,
        /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
//        /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )


    // Construct the RemoteViews object
    val views = RemoteViews(
        context.packageName,
        R.layout.quick_reminder_widget
    ).apply {
        setOnClickPendingIntent(R.id.imageButton, pendingIntent)
    }

    views.setImageViewResource(
        R.id.imageButton,
        R.drawable.ic_launcher_foreground
    )


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}