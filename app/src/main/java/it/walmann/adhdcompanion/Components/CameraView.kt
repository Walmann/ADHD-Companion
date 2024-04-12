package it.walmann.adhdcompanion.Components

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.ScaleType
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermission
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    // The camera is too big. Make it square. It also rotates?
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    modifier: Modifier = Modifier,
) {

    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Back)

//    // 1
//    val lensFacing = CameraSelector.LENS_FACING_BACK
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    val preview = Preview.Builder().build()
//    val previewView = remember { PreviewView(context) }
//    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
//    val cameraSelector = CameraSelector.Builder()
//        .requireLensFacing(lensFacing)
//        .build()

//    // 2
//    LaunchedEffect(lensFacing) {
//        val cameraProvider = context.getCameraProvider()
//        cameraProvider.unbindAll()
//        cameraProvider.bindToLifecycle(
//            lifecycleOwner,
//            cameraSelector,
//            preview,
//            imageCapture
//        )
//
//        preview.setSurfaceProvider(previewView.surfaceProvider)
//    }

    if (requestPermission(Manifest.permission.CAMERA)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
//            contentAlignment = Alignment.BottomCenter,
//            modifier = Modifier.requiredHeightIn(min = 200.dp)
            modifier = Modifier.fillMaxSize()
        ) {
//            AndroidView(
//                { previewView },
//                modifier = Modifier.fillMaxSize()
//            )


            CameraPreview( // Campose: https://github.com/ujizin/Camposer
                    modifier = Modifier
//                        .height(200.dp)
//                        .width(200.dp)
                        .fillMaxSize()
                        .padding(10.dp)
                        .padding(top = 100.dp)
                        .weight(5f),
                cameraState = cameraState,
                camSelector = camSelector,
                scaleType = ScaleType.FillCenter
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                CameraScreenButton(
//                        modifier = Modifier.size(100.dp),
                    onClick = { camSelector = camSelector.inverse },
                    contentDescription = "Turn camera around",
                    icon = R.drawable.camera_rotate_bold
                    ,
                )
                var temp1:ContentValues = ContentValues()
                var temp2: ImageCaptureResult
                CameraScreenButton(
//                        modifier = Modifier.padding(bottom = 20.dp),
                    icon = R.drawable.arrow_right,
                    onClick = {
                        Log.i("kilo", "ON CLICK")
                        cameraState.takePicture(contentValues = temp1) {result ->

                            val photoFile = File(
                                outputDirectory,
                                SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.ENGLISH).format(System.currentTimeMillis()) + ".jpg"
                            )


                            temp2 = result
                            val savedUri = Uri.fromFile(photoFile)
                            onImageCaptured(savedUri)
                            print("") // TODO NEXT Make this work. It stuck on camera. Must not be finally configured.


//                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                                val savedUri = Uri.fromFile(photoFile)
//                                onImageCaptured(savedUri)
//                            }

                        }
//                        takePhoto(
//                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
//                            imageCapture = imageCapture,
//                            outputDirectory = outputDirectory,
//                            executor = executor,
//                            onImageCaptured = onImageCaptured,
//                            onError = onError
//                        )
                    },
                )
            }
//            }
        }
    } else {
        Text(text = "Please give Camera Access")
    }
}

@Composable
fun CameraScreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Int = R.drawable.camera_bold,
    contentDescription: String = ""
) {
    IconButton(
        modifier = modifier
            .padding(bottom = 20.dp)
            .size(100.dp),
        onClick = onClick,
        content = {
            Icon(
//                imageVector = icon,
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
//                    .size(100.dp)
//                    .padding(10.dp)
                    .border(0.dp, Color.Transparent)
            )
        }
    )
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.ENGLISH).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }