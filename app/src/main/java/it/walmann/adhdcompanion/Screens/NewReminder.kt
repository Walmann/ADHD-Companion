package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.RotaryInputModifierNode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.CameraView
import it.walmann.adhdcompanion.Components.MyCameraPreview

import it.walmann.adhdcompanion.CupcakeScreen

import it.walmann.adhdcompanion.R
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun NewReminder(context: Context, modifier: Modifier) {
    lateinit var outputDirectory: File
    lateinit var cameraExecutor: ExecutorService
    lateinit var photoUri: Uri
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
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//        }
//
//        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        cameraExecutor.shutdown()
//    }


    outputDirectory = getOutputDirectory()
    cameraExecutor = Executors.newSingleThreadExecutor()

    Scaffold(
        topBar = { MyTopAppBar() },
//        floatingActionButton = {
//            FloatingActionButton(
////                containerColor = Color.Black,
//                onClick = { /*TODO*/
//
//
//                },
////                Modifier.background = Color.Red,
//                content = {
//                    Icon(
//                        Icons.Filled.Add,
//                        contentDescription = null,
////                        tint = Color.White
//                    )
//                }
//            )
//        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
//            .fillMaxSize()
//            .background(Color(0xff8d6e63))
//        ,

        ) {
            if (shouldShowCamera.value) {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = {
                        Log.e("kilo", "View error:", it)
                    }
                )
            }
            if (shouldShowPhoto.value) {
                CreateReminderForm(
                    photoUri = photoUri,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
//            MyCameraPreview(
//                context = context,
////            modifier= Modifier.weight(1f)
//            )


    }
}


@Composable
fun CreateReminderForm(photoUri: Uri, modifier: Modifier) {
    val Reminder_time = 0

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Color(0xff8d6e63))
    ) {
        Image(
            painter = rememberImagePainter(photoUri),
            contentDescription = null,
            modifier = Modifier.weight(5f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(5f)

        ) {
//            RotaryDialWidget()

            TimerButtons(text = "🕰️ Remind me in...") // Create timer dial
            TimerButtons(text = "📅 Remind at...") // Make a clock and calendar choices
            TimerButtons(text = "⏱️ Remind me in 10 minutes") // TODO Make this configurable in settings
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                ,

                horizontalArrangement = Arrangement.Center
            ) {
                NavigationButtons(text = "Cancel")

                NavigationButtons(text = "Save")
            }
        }
    }
}

@Composable
fun TimerButtons(modifier: Modifier = Modifier, text: String = "Button Text", onClick: () -> Unit = { /*TODO*/ }) {
    ElevatedButton(
        onClick = onClick,
        modifier
            .width(300.dp)
            .height(75.dp)
            .padding(vertical = 10.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun NavigationButtons(modifier: Modifier = Modifier, text: String = "Text",  onClick: () -> Unit = { /*TODO*/ }) {
    ElevatedButton(
        onClick = onClick,
        Modifier.padding(
            horizontal = 50.dp
        )

    ) {
        Text(text = text)
    }
}


