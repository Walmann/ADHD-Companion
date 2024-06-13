package it.walmann.adhdcompanion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize


class QuickReminderWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = QuickReminderWidget()
}

class QuickReminderWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent()
        }
    }

    private val actionParams = ActionParameters.Key<Boolean>(
        "createNewReminder"
    )

    @Composable
    private fun MyContent() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(R.color.ic_launcher_background)
                .clickable(
                    onClick = actionStartActivity<MainActivity>(
                        actionParametersOf(actionParams to true)
                    )
                )
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_launcher_foreground),
                contentDescription = "Create new ADHD reminder",
                modifier = GlanceModifier.fillMaxSize()
            )
        }
    }

}


//
///**
// * Implementation of App Widget functionality.
// */
//class QuickReminderWidget : AppWidgetProvider() {
//    override fun onUpdate(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetIds: IntArray
//    ) {
//        // There may be multiple widgets active, so update all of them
//        for (appWidgetId in appWidgetIds) {
//
//
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }
//    }
//
//    override fun onEnabled(context: Context) {
//        // Enter relevant functionality for when the first widget is created
//    }
//
//    override fun onDisabled(context: Context) {
//        // Enter relevant functionality for when the last widget is disabled
//    }
//}
//
//internal fun updateAppWidget(
//    context: Context,
//    appWidgetManager: AppWidgetManager,
//    appWidgetId: Int
//) {
//    val tapAction = Intent(context, MainActivity::class.java).also {
//            intent ->
//        intent.putExtra("createNewReminder", true)
//    }
//
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(
//        /* context = */ context,
//        /* requestCode = */  0,
//        /* intent = */ tapAction,
//        /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
////        /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//    )
//
//
//    // Construct the RemoteViews object
//    val views = RemoteViews(
//        context.packageName,
//        R.layout.quick_reminder_widget
//    ).apply {
//        setOnClickPendingIntent(R.id.imageButton, pendingIntent)
//    }
//
//    views.setImageViewResource(
//        R.id.imageButton,
//        R.drawable.ic_launcher_foreground
//    )
//
//
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)
//}