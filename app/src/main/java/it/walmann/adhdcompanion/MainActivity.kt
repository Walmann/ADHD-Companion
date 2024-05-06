package it.walmann.adhdcompanion

import android.Manifest
import android.app.AlarmManager
import android.app.NativeActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.navigation.NavArgs
import androidx.navigation.NavController
//import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
//import it.walmann.adhdcompanion.MyObjects.ReminderNotification
import it.walmann.adhdcompanion.MyObjects.createNotificationChannel
//import it.walmann.adhdcompanion.MyObjects.newNotification
import it.walmann.adhdcompanion.Screens.NewReminder
import it.walmann.adhdcompanion.Screens.ReminderDetails
import it.walmann.adhdcompanion.Screens.RemindersScreen
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import java.util.Calendar
import kotlin.random.Random


enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    NewReminder(title = R.string.NewReminder),
    ReminderDetails(title = R.string.screen_title_reminder_details)
}
// TODO FUTURE Check that all the reminders survive a reboot!


// TODO BEFORE RELEASE
// - Make the app prettier
// - Make sure the reminders actually shows after a long while.
// - Create App icon
// - Support all screen orientations
// - Add google login for syncing reminders


class MainActivity : ComponentActivity() {
    private lateinit var PACKAGE_NAME: String
//    private lateinit var alarmManager: AlarmManager


    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // make your action here
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied.
            }
        }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
            }

            shouldShowRequestPermissionRationale(permission) -> {

                AlertDialog(
                    onDismissRequest = { },
                    text = { Text(text = "Notification permission is required for this app to work properly") },

                    confirmButton = {
                        TextButton(onClick = {
                            val uri: Uri =
                                Uri.fromParts("package", this.PACKAGE_NAME, null)
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                data = uri
                                startActivity(this)
                            }
                        }) { Text(text = "Go to settings") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                if (this.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                                    val packageManager: PackageManager = this.packageManager
                                    val intent: Intent =
                                        packageManager.getLaunchIntentForPackage(this.packageName)!!
                                    val componentName: ComponentName = intent.component!!
                                    val restartIntent: Intent =
                                        Intent.makeRestartActivityTask(componentName)
                                    this.startActivity(restartIntent)
                                    Runtime.getRuntime().exit(0)
                                } else {
                                }
                            },
                        ) { Text(text = "Done") }
                    }
                )
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        PACKAGE_NAME = applicationContext.packageName
        super.onCreate(savedInstanceState)

//        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        setContent {
            ADHDCompanionTheme {
//                window.navigationBarColor(@ColorInt )
                ADHDCompanionApp(modifier = Modifier, context = this)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    CheckNotificationPermission()
                }
            }
        }

    }


}

@Composable
fun ADHDCompanionApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    context: Context
) {

    createNotificationChannel(context = context)

//    requestPermissionNotifications(context = context)


    NavHost(
        navController = navController,
//        startDestination = CupcakeScreen.NewReminder.name,
        startDestination = CupcakeScreen.Start.name,
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//                .padding(innerPadding)
    ) {

        composable(route = CupcakeScreen.Start.name) {
            RemindersScreen(
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
                ,
                navController,
                context = context

            )
        }
        composable(route = CupcakeScreen.NewReminder.name) {
            NewReminder(
                context = context,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            )
        }
        composable(
            route = "${CupcakeScreen.ReminderDetails.name}/{calendarId}",
            arguments = listOf(navArgument(name = "calendarId") {
                type = NavType.StringType
            }
            )
        ) {
            val curCalendar = it.arguments?.getString("calendarId") ?: ""

            ReminderDetails(
                context = context,
                navController = navController,
                calendarId = curCalendar,
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

//fun navigateWithCalender(calendar: Calendar, navController: NavController): Unit {
//    // Sender Calendar-objektet til destinasjonsmålet "my_destination"
//    navController.navigate("my_destination", NavArgs.Builder()
//        .put("calendar", calendar)
//        .build())
//
//// Henter Calendar-objektet fra NavArgs i destinasjonsmålet
//    val calendarObject: Calendar? = navController.currentBackStackEntry?.arguments?.getParcelable("calendarObject")
//
//}