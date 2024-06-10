package it.walmann.adhdcompanion.Components.myCamera


import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.window.core.layout.WindowWidthSizeClass
import com.google.common.util.concurrent.ListenableFuture
import it.walmann.adhdcompanion.R
import java.io.File
import java.util.concurrent.Executors

@Composable
fun MyCameraPreview(
    modifier: Modifier = Modifier,
    context: Context,
    outputFile: File,
    onImageCaptured: (Uri) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(context.createNewFile()).build()

    val imageCapture = buildImageCapture()
    val lensFacing = remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }

    val previewViewSize = remember {
        mutableStateOf<Rect>(Rect())
    }


    val rotateCamera = {
        lensFacing.value =
            if (CameraSelector.LENS_FACING_FRONT == lensFacing.value) CameraSelector.LENS_FACING_BACK
            else CameraSelector.LENS_FACING_FRONT
    }

    val takeImage = {
        imageCapture.takePicture(outputFileOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CamTest3", "Saved image!")

                    onImageCaptured(outputFileResults.savedUri ?: "".toUri())

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("CamTest3", "Error when taking picture! ${exception}")
                }
            })

    }
    if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
        Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            MyCam(
                modifier = Modifier.weight(7f),
                imageCapture = imageCapture,
                lensFacing = lensFacing.value,
            )
            ButtonSection(
                modifier = Modifier
                    .weight(3f)
                ,
                onRotateCameraClick = rotateCamera,
                onTakeImage = takeImage
            )
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            MyCam(
                modifier = Modifier.weight(7f),
                imageCapture = imageCapture,
                lensFacing = lensFacing.value,
            )
            ButtonSection(
                modifier = Modifier.weight(3f),
                onRotateCameraClick = rotateCamera,
                onTakeImage = takeImage
            )
        }
    }


}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier, onRotateCameraClick: () -> (Unit), onTakeImage: () -> (Unit)
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        CameraScreenButton(
            onClick = onRotateCameraClick,
            contentDescription = "Turn camera around",
            icon = R.drawable.camera_rotate_bold,
        )
        CameraScreenButton(
            icon = R.drawable.arrow_right, onClick = onTakeImage
        )
    }
}

@Composable
private fun MyCam(
    modifier: Modifier = Modifier,
    imageCapture: ImageCapture,
    lensFacing: Int,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var cameraProvider: ProcessCameraProvider? = null
    DisposableEffect(key1 = cameraProvider) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }
    Box(modifier = modifier.wrapContentSize()) {
        AndroidView(modifier = Modifier.fillMaxSize(),
            factory = { androidViewContext -> initPreviewView(androidViewContext) },
            update = { previewView: PreviewView ->
                val cameraSelector: CameraSelector = buildCameraSelector(lensFacing)
//                val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                    ProcessCameraProvider.getInstance(context)


                cameraProviderFuture.addListener({
                    cameraProvider = cameraProviderFuture.get()

                    val preview = buildPreview().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    try {
                        cameraProvider?.let {
                            it.unbindAll() //Make sure we only use 1 usecase related to camera

                            val camera = it.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageCapture
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("CameraPreview", "CameraPreview: ${e.localizedMessage}")
                    }
                }, ContextCompat.getMainExecutor(context))
            })

    }
}

private fun initPreviewView(androidViewContext: Context): PreviewView {
    val previewView = PreviewView(androidViewContext).apply {
        this.clipToOutline = true
        this.scaleType = PreviewView.ScaleType.FIT_CENTER
        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
    }
    return previewView
}

private fun getResolutionSelector(): ResolutionSelector {
    return ResolutionSelector.Builder()
//        .setAspectRatioStrategy(
//            AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY
//        )
//        .setResolutionStrategy(
//            ResolutionStrategy(
//                Size(500, 500),
//                ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER
//            )
//        )
        .build()
}

//private fun getResolutionSelector(): ResolutionSelector {
//    return ResolutionSelector.Builder().setResolutionStrategy(
//        ResolutionStrategy(
//            Size(500, 500),
//            ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER
//        )
//    ).build()
//}

private fun buildPreview(): Preview {
    return Preview.Builder().setResolutionSelector(getResolutionSelector()).build()
}

private fun buildImageCapture(): ImageCapture {
    return ImageCapture.Builder().setResolutionSelector(getResolutionSelector()).build()
}

private fun buildCameraSelector(cameraLens: Int): CameraSelector {
    return CameraSelector.Builder().requireLensFacing(cameraLens).build()
}


@Composable
fun CameraScreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Int = R.drawable.camera_bold,
    contentDescription: String = ""
) {
    IconButton(modifier = modifier
        .padding(bottom = 20.dp)
        .size(100.dp),
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .border(0.dp, Color.Transparent)
            )
        })
}


fun Context.createNewFile() = File( // TODO Clean up this page. It should be organized better.
    filesDir, "${System.currentTimeMillis()}.jpg"
).apply { createNewFile() }


