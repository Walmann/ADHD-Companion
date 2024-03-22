package it.walmann.adhdcompanion.Components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.provider.Settings
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//@Composable
//fun CameraWidget() {
//    val lensFacing = CameraSelector.LENS_FACING_BACK
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current
//    val preview = Preview.Builder().build()
//    val previewView = remember {
//        PreviewView(context)
//    }
//    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
//    LaunchedEffect(lensFacing) {
//        val cameraProvider = context.getCameraProvider()
//        cameraProvider.unbindAll()
//        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview)
//        preview.setSurfaceProvider(previewView.surfaceProvider)
//    }
//    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
//}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }


@Composable
fun MyCameraPreview(context: Context, modifier: Modifier = Modifier) {


//    val context = LocalContext.current

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)


    var isPermissionGranted by remember {
        mutableStateOf<Boolean?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isPermissionGranted = isGranted
    }
    when (isPermissionGranted) {
        true -> CameraScreen(cameraProviderFuture, context = context, modifier)
        false -> Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.re_request_Camera_Permission), color = Color.Black)
            Button(onClick = { openAppSettings(context = context) }) { Text(text = "Open App Settings") }
        }

        null -> {
            SideEffect {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}


@Composable
fun CameraScreen(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    context: Context,
    modifier: Modifier
) {

    Column(modifier.fillMaxSize()) {
        CameraPreview(
            cameraProviderFuture,
            context,
            modifier
                .fillMaxSize()
                .weight(1.5f)
        )
    }
}
//        BottomAppBar(modifier
//            .weight(1f)
//            .fillMaxSize()
//            .background(Color.Red)
//        ) {
//            IconButton(onClick = { /*TODO*/ }, modifier.fillMaxSize()) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
//            }
//        }


//    }
//}


@Composable
fun CameraPreview(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    context: Context,
    modifier: Modifier
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        factory = { factoryContext ->
            PreviewView(context).apply {
                //                    setBackgroundColor(Color.Green)

                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                post {
                    cameraProviderFuture.addListener(Runnable {
                        val cameraProvider = cameraProviderFuture.get()
                        bindPreview(
                            cameraProvider,
                            lifecycleOwner,
                            this,
                        )
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        }
    )
}

fun bindPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
) {
    val preview: Preview = Preview.Builder()
//            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
        .build()

    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    preview.setSurfaceProvider(previewView.surfaceProvider)

    var camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
}

fun openAppSettings(context: Context) {
    val packageName = PACKAGE_NAME
    val uri = Uri.fromParts("package", context.packageName, null)
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    intent.setData(uri)

    startActivity(context, intent, null)
}