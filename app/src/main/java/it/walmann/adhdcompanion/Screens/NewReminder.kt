package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.AutoResizeText
import it.walmann.adhdcompanion.Components.CameraView
import it.walmann.adhdcompanion.Components.DateSelectorDialog
import it.walmann.adhdcompanion.Components.FontSizeRange
import it.walmann.adhdcompanion.Components.TimeSelectDialog
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.R
import org.checkerframework.checker.units.qual.min
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
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxSize(),
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
                    modifier = modifier //.fillMaxSize()


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
    val timePickerState = rememberTimePickerState()


    val openDateAndTimerDialog = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(20.dp)
//            .padding(top = 10.dp)
    ) {


        Image(
            painter = rememberImagePainter(photoUri),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier
                .weight(7f)
                //                .fillMaxWidth()
//                .aspectRatio(6.1f)
                .fillMaxSize()
                .heightIn(50.dp, 100.dp)

        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .heightIn(50.dp, 50.dp)
                .weight(10f)

        ) {
            val buttonRoundness = 30.dp


            Text(text = reminderCalendar.time.toString())


            Button(
                onClick = {
                    openTimerDialog.value = !openDateAndTimerDialog.value
                },
                shape = RoundedCornerShape(topStart = buttonRoundness, topEnd = buttonRoundness),
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()

            ) {// https://stackoverflow.com/questions/63971569/androidautosizetexttype-in-jetpack-compose
//                "${reminderCalendar.get(Calendar.HOUR).toString().padStart(2, '0')}:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",

                AutoResizeText(
                    text = "${reminderCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')}:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",
                    maxLines = 1,

                    fontSizeRange = FontSizeRange(
                        min = 10.sp,
                        max = 70.sp,
                    ),
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
                AutoResizeText(
                    text = "${
                        reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')
                    }.${
                        reminderCalendar.get(Calendar.MONTH).toString().padStart(2, '0')
                    }.${reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')}",
                    maxLines = 1,
//                    modifier = Modifier.padding(20.dp),
                    fontSizeRange = FontSizeRange(
                        min = 10.sp,
                        max = 60.sp,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    reminderCalendar.add(Calendar.MINUTE, 10)
                },
                shape = RoundedCornerShape(buttonRoundness),
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth()
            ) {
                AutoResizeText(
                    text = "⏱️ Remind me in 10 minutes",
                    maxLines = 1,
                    fontSizeRange = FontSizeRange(
                        min = 10.sp,
                        max = 40.sp,
                    ),
                )
//                Text(
//                    "⏱️ Remind me in 10 minutes",
//                    fontSize = 20.sp,
//                    modifier = Modifier.padding(vertical = 5.dp)
//                )
            }
            Spacer(modifier = Modifier.height(1.dp))
//            TimerButtons(
////                text = "${reminderCalendar.get(Calendar.HOUR).toString().padStart(2, '0')}:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",
//                onClick = {
//                    openTimerDialog.value = !openDateAndTimerDialog.value
//                },
//
//            ){
//                Text(
//                    text = "${reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')}.${reminderCalendar.get(Calendar.MONTH).toString().padStart(2, '0')}.${reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')}",
//                    fontSize = 20.sp
//                )
//            }


//            TimerButtons(
//                text = "Remind me in 10 minutes",
//                onClick = {
//                    reminderCalendar.add(Calendar.MINUTE, 10)
//                }
//            ){
//                Text(text = "Remind me in 10 minutes")
//            }


//            TimerButtons(
//                text = "🕰️ Remind me in...",
//                onClick = {
//                    openTimerDialog.value = true
//                }
//            )
//
//            TimerButtons(
//                text = "📅 Remind at...", // TODO WORKNOW Create a "remind me on..." selector where you can select date and time for reminder.
//                onClick = {
//                    openDateAndTimerDialog.value = true
//                }
//            ) // Make a clock and calendar choices
//
//                TimerButtons(
//                    text = "⏱️ Remind me in 10 minutes",
////                onClick = {} // TODO Make this configurable in settings
//                )


            if (openTimerDialog.value) {
                TimeSelectDialog(
                    // TODO Make this prettier
                    dialogTitle = "Select time until next reminder",
//                    calendar = reminderCalendar,
                    onConfirmRequest = {
                        reminderCalendar = it
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    onDismissRequest = {
//                        openTimerDialog.value = !openTimerDialog.value
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                )
            }


            if (openDateAndTimerDialog.value) {
                DateSelectorDialog(
//                    modifier = Modifier
//                        .height(200.dp)
//                        .width(200.dp),
                    onDismissRequest = {
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    onConfirmRequest = {
                        reminderCalendar = it
//                        openTimerDialog.value = !openTimerDialog.value
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
                    .padding(bottom = 15.dp),

                ) {
                NavigationButtons(
                    text = "Cancel",
                    onClick = {},
                )
                NavigationButtons(
                    text = "Save",
                    onClick = {
                        newReminder.saveNewReminder(
                            context = context,
                            reminderTime = reminderCalendar
                        )
                        navController.navigate(CupcakeScreen.Start.name)
                    }
                )
            }
        }
    }
}

//@Composable
//fun TimerButtons(
//    modifier: Modifier = Modifier,
//    text: String = "Button Text",
//    onClick: () -> Unit = {},
//    content: @Composable () -> Unit,
//) {
//    ElevatedButton(
//        onClick = {
//            onClick()
//        },
//        modifier = modifier
//            .width(300.dp)
//            .requiredHeightIn(min = 75.dp)
////            .padding(vertical = 10.dp),
//    ) {
//        content()
////        Text(text = text)
//    }
//}


@Composable
fun NavigationButtons(
    modifier: Modifier = Modifier,
    text: String = "Text",
    onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick,

        ) {
        AutoResizeText(
            text = text,
            maxLines = 1,
            modifier = modifier
                .padding(10.dp)
                .height(50.dp),
            fontSizeRange = FontSizeRange(
                min = 10.sp,
                max = 40.sp,
            ),
        )
//        Text(
//            text = text,
//            modifier = modifier.padding(10.dp)
//        )
    }
}


@Preview(widthDp = 720, heightDp = 1280)
@Preview(widthDp = 680, heightDp = 2000)
@Composable
private fun NewReminderPreview() {
    CreateReminderForm(
        context = LocalContext.current,
        photoUri = Uri.parse("android.resource://it.walmann.adhdcompanion/${R.drawable.placeholder_reminderimage}"),
        modifier = Modifier,
        navController = rememberNavController()
    )
}