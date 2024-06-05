package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import it.walmann.adhdcompanion.Components.myCamera.MyCameraPreview
import it.walmann.adhdcompanion.requestPermissionCamera
import java.io.File
import java.util.Calendar


@Composable
fun CameraView(
    modifier: Modifier = Modifier,
//    executor: Executor,
    onImageCaptured: (Uri) -> Unit = {},
//    onError: (ImageCaptureException) -> Unit = {},
    context: Context,
//    outputDirectory: File,
) {
    if (requestPermissionCamera(context)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Column {
                MyCameraPreview(
                    modifier
                        .fillMaxWidth()
                        .weight(7f),
                    context = context,
                    onImageCaptured = onImageCaptured,
                    outputFile = File(
                        Calendar.getInstance().timeInMillis.toString()
                    )
                )
            }
        }

    } else {
        Text(text = "Please give Camera Access")
    }
}
