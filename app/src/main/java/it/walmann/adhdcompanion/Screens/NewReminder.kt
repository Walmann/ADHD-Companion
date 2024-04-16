package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.CameraView
import it.walmann.adhdcompanion.Components.TimeSelectDialog
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.File
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
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .padding(innerPadding)
                .background(Color(0xff8d6e63))
                .padding(top = 10.dp)
//                .fillMaxSize()
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max)


//            .background(Color(0xff8d6e63))
//        ,

        ) {
            if (shouldShowCamera.value) {
                CameraView(
                    context = context,
                    modifier = modifier.fillMaxSize(),
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
                    modifier = Modifier.fillMaxSize(),

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
    var reminderTime by remember { mutableStateOf("StringPlaceholder") }
    val showButtons = remember { mutableStateOf(true) }
    val buttonBackOrCancel = remember { mutableStateOf("Cancel") }

    val newReminder by remember { mutableStateOf(myReminder()) }
    newReminder.reminderTime = reminderTime
    newReminder.reminderImage = photoUri

    val openTimerDialog = remember {mutableStateOf(false)}


    Image(
        painter = rememberImagePainter(photoUri),
        contentDescription = null,
        modifier = modifier
//                .weight(1f)
            .requiredHeight(200.dp)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
//                .weight(5f)
//                .fillMaxWidth()
    ) {
//            Text(text = reminderTime.toString())
        if (showButtons.value) {
            buttonBackOrCancel.value = "Cancel"
            TimerButtons(
                text = "🕰️ Remind me in...",
                onClick = {
                    showButtons.value = !showButtons.value
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

        }
        if (!showButtons.value) {
            buttonBackOrCancel.value = "Back"
            TimeSelectDialog(
                dialogTitle = "Select time until next reminder",
//                    viewModel = viewModel
                onConfirmRequest = {
                    reminderTime = it
                    showButtons.value = !showButtons.value
                },
                onDismissRequest = {
                    showButtons.value = !showButtons.value
                }
            )
//                TimeSelectBox(
//                    dialogTitle = "Select time until next reminder",
////                    viewModel = viewModel
//                    onValueChange = {
//                        reminderTime = it
//                    }
//                )

        }
        Row(
//                verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
//                    .padding(bottom = 50.dp)
            ,

            horizontalArrangement = Arrangement.Center
        ) {
            NavigationButtons(
                text = buttonBackOrCancel.value,
                onClick = {
                    showButtons.value = !showButtons.value
                },
            )
            NavigationButtons(text = "Save", onClick = {
                newReminder.saveNewReminder(context = context)
                navController.navigate(CupcakeScreen.Start.name)
            })
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
        modifier
            .width(300.dp)
            .requiredHeightIn(min = 75.dp)
            .padding(vertical = 10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = text)
//            if (showSubWidget.value) {
//                TimeSelectBox(
//                    onDismissRequest = {},
//                    onConfirmation = {},
//                    dialogTitle = "Select time until next reminder"
//                )
//            }
        }
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
        modifier.padding(
            horizontal = 50.dp
        )

    ) {
        Text(text = text)
    }
}


