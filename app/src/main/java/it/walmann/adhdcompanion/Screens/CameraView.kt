package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.walmann.adhdcompanion.Components.myCamera.MyCameraPreview
import it.walmann.adhdcompanion.R
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
//        }
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
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .border(0.dp, Color.Transparent)
            )
        }
    )
}
