package it.walmann.adhdcompanion.Components

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Size
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.CameraState
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.ImageTargetSize
import com.ujizin.camposer.state.ScaleType
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.requestPermission
import java.io.File
import java.util.concurrent.Executor


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    // The camera is too big. Make it square. It also rotates?
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {

    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Back)


    if (requestPermission(Manifest.permission.CAMERA)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
//            contentAlignment = Alignment.BottomCenter,
//            modifier = Modifier.requiredHeightIn(min = 200.dp)
            modifier = Modifier.fillMaxSize()
        ) {
            CameraPreview( // Campose: https://github.com/ujizin/Camposer

//                imageCaptureTargetSize = ImageTargetSize(
////                    aspectRatio = AspectRatio.RATIO_4_3,
////                    size = Size(500,500),
//                    outputSize = CameraController.OutputSize(Size(500,500))
//                ),
                cameraState = cameraState,
                camSelector = camSelector,
                scaleType = ScaleType.FitCenter,
                modifier = modifier
                    .padding(10.dp)
                    .weight(3f)
                ,
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