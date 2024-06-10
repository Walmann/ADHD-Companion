package it.walmann.adhdcompanion


import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import it.walmann.adhdcompanion.Handlers.Reminder.ReminderDatabase
import it.walmann.adhdcompanion.MyObjects.createNotificationChannel
import it.walmann.adhdcompanion.Screens.BlankStartpage
import it.walmann.adhdcompanion.Screens.NewReminder
import it.walmann.adhdcompanion.Screens.RemindersScreen
import it.walmann.adhdcompanion.Screens.SettingsScreen
import it.walmann.adhdcompanion.Screens.SingleReminderForm
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ReminderList(title = R.string.remindersScreenTitle),
    NewReminder(title = R.string.NewReminder),
    ReminderDetails(title = R.string.screen_title_reminder_details),
    SettingsScreen(title = R.string.settings_screen),
}

/* TODO
 - Make Monochrome Icon
 - Add google login for syncing reminders
    - Resize images to smaller size
 - Make the app prettier
 */

class MainActivity : ComponentActivity() {
    private lateinit var PACKAGE_NAME: String
//    private lateinit var alarmManager: AlarmManager

    companion object {
        lateinit var reminderDB: ReminderDatabase
        lateinit var navigator: NavHostController
    }


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
//        initSettings(context = context)


//        ThingsToTest(context = this)


        setContent {
            ADHDCompanionTheme {
//                window.navigationBarColor(@ColorInt )
                ADHDCompanionApp(
                    modifier = Modifier,
                    context = this,
                    reminderUID = intent.getStringExtra("reminderUID")
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    CheckNotificationPermission()
                }
            }
        }

    }


}

@Composable
fun ADHDCompanionApp(
    modifier: Modifier,
    context: Context,
    reminderUID: String?
) {

    createNotificationChannel(context = context)

    MainActivity.navigator = rememberNavController()

//    CoroutineScope(Dispatchers.Default).launch {
//        initSettings(context = context)
//    }

//    CoroutineScope(Dispatchers.Default).launch {
//        debugTestSQLdb(context)
//    }

//    requestPermissionNotifications(context = context)

    CoroutineScope(Dispatchers.Default).launch {
        MainActivity.reminderDB = Room.databaseBuilder(
            context, ReminderDatabase::class.java, "reminder"
        ).allowMainThreadQueries().build()
    }


    val EmptyDBOnStartup = !true

    if (EmptyDBOnStartup) {

        Thread.sleep(500)

        MainActivity.reminderDB.ReminderDao().getAll().forEach { x ->
            MainActivity.reminderDB.ReminderDao().delete(x)
        }

        val temp123 = MainActivity.reminderDB.ReminderDao().getAll()
        print("")
    }
    NavHost(
        navController = MainActivity.navigator,
//        startDestination = CupcakeScreen.NewReminder.name,
        startDestination = CupcakeScreen.Start.name,
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//                .padding(innerPadding)
    ) {

        composable(route = CupcakeScreen.Start.name) {
            BlankStartpage(reminderUID = reminderUID)
        }
        composable(route = CupcakeScreen.ReminderList.name) {
            RemindersScreen(
                modifier = Modifier
                    .fillMaxSize(),
                context = context,
            )
        }
        composable(route = CupcakeScreen.NewReminder.name) {
            NewReminder(
                context = context,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        composable(
            route = "${CupcakeScreen.ReminderDetails.name}/{calendarId}",
            arguments = listOf(navArgument(name = "calendarId") {
                type = NavType.StringType
            }
            )
        ) {
            val curReminder = it.arguments?.getString("calendarId") ?: ""
            SingleReminderForm(
                context = context,
                reminderID = curReminder.toLong(),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        composable(route = CupcakeScreen.SettingsScreen.name) {
            SettingsScreen()
        }
    }
}


