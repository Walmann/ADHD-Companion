package it.walmann.adhdcompanion.Screens

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.AutoResizeText
import it.walmann.adhdcompanion.Components.CameraView
import it.walmann.adhdcompanion.Components.DateSelectorDialog
import it.walmann.adhdcompanion.Components.FontSizeRange
import it.walmann.adhdcompanion.Components.TimeSelectDialog
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermissionExactAlarm
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import java.io.File
import java.util.Calendar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Composable
fun NewReminder(context: Context, modifier: Modifier, navController: NavController) {
    lateinit var outputDirectory: File
    var photoUri: Uri = Uri.EMPTY
    val shouldShowPhoto: MutableState<Boolean> = remember { mutableStateOf(false) }
    val shouldShowCamera: MutableState<Boolean> = remember { mutableStateOf(true) }


    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
    }

    fun getOutputDirectory(): File {
        val files = context.filesDir
        return files
    }




    outputDirectory = getOutputDirectory()
    var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    Scaffold(
        topBar = { MyTopAppBar() },

        ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
//                .background(Color(0xff8d6e63))
                .padding(10.dp)
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max)
        ) {
            if (shouldShowCamera.value) {
                CameraView(
                    context = context,
                    modifier = Modifier,
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = {
                        Log.e("kilo", "View error:", it)
                    },

                    )

            }

            if (shouldShowPhoto.value) {
                CreateReminderForm(
                    context = context,
                    photoUri = photoUri,
                    navController = navController,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReminderForm(
    context: Context,
    photoUri: Uri,
    modifier: Modifier,
    navController: NavController,
//    viewModel: ReminderViewModel = ReminderViewModel()
) {


    var reminderCalendar by remember { mutableStateOf<Calendar>(Calendar.getInstance()) }


    val newReminder by remember { mutableStateOf(myReminder(reminderCalendar = reminderCalendar)) }
    newReminder.reminderCalendar = reminderCalendar
    newReminder.reminderImage = photoUri

    val openTimerDialog = remember { mutableStateOf(false) }
    val openDateAndTimerDialog = remember { mutableStateOf(false) }


    // Ask for all needed permissions
//    requestPermissionExactAlarm(context, aManager = )


//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        requestPermission(permission = Manifest.permission.SCHEDULE_EXACT_ALARM)
//    }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
//            .padding(20.dp)
    ) {


        Image(
            painter = rememberAsyncImagePainter(photoUri),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier
                .weight(7f)
//                .fillMaxSize()
                .padding(vertical = 10.dp)
//                .heightIn(50.dp, 100.dp)

        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
//                .heightIn(50.dp, 50.dp)
                .weight(10f)

        ) {
            val buttonRoundness = 30.dp

//            Text(text = reminderCalendar.time.toString())

            Button(
                onClick = {
                    openTimerDialog.value = !openDateAndTimerDialog.value
                },
                shape = RoundedCornerShape(topStart = buttonRoundness, topEnd = buttonRoundness),
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "${
                        reminderCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
                    }:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",
                    maxLines = 1,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Button(
                onClick = {
                    openDateAndTimerDialog.value = !openDateAndTimerDialog.value
                },
                shape = RoundedCornerShape(
                    bottomStart = buttonRoundness,
                    bottomEnd = buttonRoundness
                ),
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "${
                        reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')
                    }.${
                        reminderCalendar.get(Calendar.MONTH).toString().padStart(2, '0')
                    }.${reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')}",
                    maxLines = 1,
                    style = MaterialTheme.typography.displayMedium

                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            RemindMeButtons(
                onClick = {
                    reminderCalendar.add(Calendar.MINUTE, 10)
                    saveReminder(context, reminderCalendar, newReminder, navController)
                },
                text = "⏱\uFE0F Remind me in 10 minutes"
            ) // TODO SETTINGS Make this configurable in settings

            Spacer(modifier = Modifier.height(1.dp))
            RemindMeButtons(onClick = {
                reminderCalendar.add(Calendar.SECOND, 10)
                saveReminder(context, reminderCalendar, newReminder, navController)
            }, text = "⏱️ Remind me in 10 seconds")

            Spacer(modifier = Modifier.height(1.dp))
            RemindMeButtons(onClick = {
                reminderCalendar.add(Calendar.SECOND, 20)
                saveReminder(context, reminderCalendar, newReminder, navController)
            }, text = "⏱️ Remind me in 20 seconds")
            Spacer(modifier = Modifier.height(1.dp))



            if (openTimerDialog.value) {
                TimeSelectDialog(
                    // TODO Make this prettier
                    dialogTitle = "Select time until next reminder",
                    onConfirmRequest = {
                        reminderCalendar = it
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    onDismissRequest = {
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                )
            }


            if (openDateAndTimerDialog.value) {
                DateSelectorDialog(
                    onDismissRequest = {
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    onConfirmRequest = {
                        reminderCalendar = it
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    calendar = reminderCalendar
                )
            }


            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(10f)
                    .padding(vertical = 10.dp),

                ) {
                NavigationButtons(
                    text = "Cancel",
                    onClick = {},
                )
                NavigationButtons(
                    text = "Save",
                    onClick = {
                        saveReminder(context, reminderCalendar, newReminder, navController)
                    }
                )
            }
        }
    }
}

fun saveReminder(
    context: Context,
    reminderCalendar: Calendar,
    newReminder: myReminder,
    navController: NavController
) {
    newReminder.SaveNewReminder(
        context = context,
        reminderTime = reminderCalendar
    )
    navController.navigate(CupcakeScreen.Start.name)
}


@Composable
fun RemindMeButtons(modifier: Modifier = Modifier, onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = text,
            maxLines = 1,
//            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun NavigationButtons(
    modifier: Modifier = Modifier,
    text: String = "Text",
    onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick,
        modifier.height(50.dp)
    ) {
//        AutoResizeText(
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
//@PreviewLightDark
@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420")
//@Preview(widthDp = 680, heightDp = 2000)
@Composable
private fun NewReminderPreview() {
    val context = LocalContext.current
//    val resources = context.resources
    val photoUri =
        Uri.parse("android.resource://it.walmann.adhdcompanion/" + R.drawable.placeholder_reminderimage)
    ADHDCompanionTheme {
        Surface {
            CreateReminderForm(
                context = context,
                photoUri = photoUri,
                modifier = Modifier,
                navController = rememberNavController()
            )
        }
    }
}
