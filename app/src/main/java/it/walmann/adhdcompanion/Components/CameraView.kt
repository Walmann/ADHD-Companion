package it.walmann.adhdcompanion.Components

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Size
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.CameraState
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.ImageTargetSize
import com.ujizin.camposer.state.ScaleType
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermission
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    // The camera is too big. Make it square. It also rotates?
    modifier: Modifier = Modifier,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit = {},
    onError: (ImageCaptureException) -> Unit = {},
    context: Context,
    outputDirectory: File,
) {

    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Back)


    if (requestPermission(Manifest.permission.CAMERA)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
//            contentAlignment = Alignment.BottomCenter,
//            modifier = Modifier.requiredHeightIn(min = 200.dp)
            modifier = modifier.fillMaxSize()
        ) {
            CameraPreview( // CameraPreview extends beyond borders. Submitted bugreport.
                // Campose: https://github.com/ujizin/Camposer
                cameraState = cameraState,
                camSelector = camSelector,
                scaleType = ScaleType.FitStart,
                modifier = Modifier
                    .padding(10.dp)
//                    .fillMaxHeight()
                    .weight(7f)

                ,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                CameraScreenButton(
//                        modifier = Modifier.size(100.dp),
                    onClick = { camSelector = camSelector.inverse },
                    contentDescription = "Turn camera around",
                    icon = R.drawable.camera_rotate_bold,
                )
                CameraScreenButton(
//                        modifier = Modifier.padding(bottom = 20.dp),
                    icon = R.drawable.arrow_right,
                    onClick = {
                        myTakePhoto(
                            cameraState = cameraState,
                            context = context,
                            onImageCaptured = onImageCaptured,
                        )

                    }
                )
            }

        }
//            }
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

private fun Context.createNewFile() = File(
    filesDir, "${System.currentTimeMillis()}.jpg"
).apply { createNewFile() }

private fun myTakePhoto(
    cameraState: CameraState,
    context: Context,
    onImageCaptured: (Uri) -> Unit,
) {
    cameraState.takePicture(
        context.createNewFile(),

        ) { result ->
        if (result is ImageCaptureResult.Success) {

            onImageCaptured(result.savedUri!!)
        } else {
            Throwable("Error capturing image!")
        }

    }
}
