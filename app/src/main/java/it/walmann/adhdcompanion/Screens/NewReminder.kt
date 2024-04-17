package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.CameraView
import it.walmann.adhdcompanion.Components.TimeSelectDialog
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.File
import java.time.LocalDateTime
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

@Composable
fun CreateReminderForm(
    context: Context,
    photoUri: Uri,
    modifier: Modifier,
    navController: NavController,
//    viewModel: ReminderViewModel = ReminderViewModel()
) {
    var reminderTime by remember { mutableStateOf<LocalDateTime>(LocalDateTime.now()) }
//    val buttonBackOrCancel = remember { mutableStateOf("Cancel") }

    val newReminder by remember { mutableStateOf(myReminder()) }
    newReminder.reminderTime = reminderTime
    newReminder.reminderImage = photoUri

    val openTimerDialog = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
//            .padding(top = 10.dp)
    ) {


        Image(
            painter = rememberImagePainter(photoUri),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier
                .weight(1f)
                .heightIn(50.dp, 100.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .weight(1f)

        ) {
            Text(text = reminderTime.toString())

                TimerButtons(
                    text = "🕰️ Remind me in...",
                    onClick = {
                        openTimerDialog.value = !openTimerDialog.value
                    }
                )

//                TimerButtons(
//                    text = "📅 Remind at...",
//                ) // Make a clock and calendar choices
//
//                TimerButtons(
//                    text = "⏱️ Remind me in 10 minutes",
////                onClick = {} // TODO Make this configurable in settings
//                )


            if (openTimerDialog.value) {
                TimeSelectDialog(
                    dialogTitle = "Select time until next reminder",
                    onConfirmRequest = {
                        reminderTime = it
                        openTimerDialog.value = !openTimerDialog.value
                    },
                    onDismissRequest = {
                        openTimerDialog.value = !openTimerDialog.value
                    }
                )

            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)

                ,

            ) {
//                NavigationButtons(
//                    text = "Cancel",
//                    onClick = {
//                    },
//                )
                NavigationButtons(
                    text = "Save",
                    onClick = {
                    newReminder.saveNewReminder(context = context, reminderTime = reminderTime)
                    navController.navigate(CupcakeScreen.Start.name)
                })
            }
        }
    }
}

@Composable
fun TimerButtons(
    modifier: Modifier = Modifier,
    text: String = "Button Text",
    onClick: () -> Unit = {}
) {
    val showSubWidget = remember { mutableStateOf(false) }
    ElevatedButton(
        onClick = {
            showSubWidget.value = true
            onClick()
        },
        modifier = modifier
            .width(300.dp)
            .requiredHeightIn(min = 75.dp)
            .padding(vertical = 10.dp),
    ) {
        Text(text = text)
    }
}


@Composable
fun NavigationButtons(
    modifier: Modifier = Modifier,
    text: String = "Text",
    onClick: () -> Unit = { }
) {
    ElevatedButton(
        onClick = onClick,

    ) {
        Text(text = text,
        modifier = modifier.padding(10.dp))
    }
}


