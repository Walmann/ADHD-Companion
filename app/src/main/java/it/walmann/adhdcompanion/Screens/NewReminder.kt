package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.MainActivity
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Composable
fun NewReminder(
    context: Context,
    modifier: Modifier,
) {
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
//        topBar = { MyTopAppBar() },
//
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
                SingleReminderForm(
                    context = context,
                    photoUri = photoUri,
                    navController = MainActivity.navigator,
                    isBeingInitialized = true,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}







