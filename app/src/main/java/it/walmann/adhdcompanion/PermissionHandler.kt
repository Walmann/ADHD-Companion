package it.walmann.adhdcompanion

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun requestPermission(permission: String): Boolean {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionState.value = isGranted
    }

    LaunchedEffect(key1 = true) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            launcher.launch(permission)
        } else {
            permissionState.value = true
        }
    }

    return permissionState.value
}



//fun Context.isPermissionGranted(name: String): Boolean {
//    return ContextCompat.checkSelfPermission(
//        this, name
//    ) == PackageManager.PERMISSION_GRANTED
//}
//
//fun Activity.shouldShowRationale(name: String): Boolean {
//    return shouldShowRequestPermissionRationale(name)
//}
//
//fun Context.hasCameraPermission(): Boolean {
//
//    return when {
//        // If Android Version is Greater than Android Pie!
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> true
//
//        else -> isPermissionGranted(name = Manifest.permission.CAMERA)
//    }
//}
//
//fun Context.gotoApplicationSettings() {
//    startActivity(Intent().apply {
//        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        data = Uri.parse("package:${packageName}")
//    })
//}
//
//fun Context.findActivity(): Activity? {
//    return when (this) {
//        is Activity -> this
//        is ContextWrapper -> {
//            baseContext.findActivity()
//        }
//
//        else -> null
//    }
//}

//fun SnackbarHostState.showSnackBar(
//    message: String? = null,
//    action: String? = null,
//    duration: SnackbarDuration = Short,
//    coroutineScope: CoroutineScope,
//    onSnackBarAction: () -> Unit = {},
//    onSnackBarDismiss: () -> Unit = {},
//) {
//    if (!message.isNullOrEmpty()) {
//
//        coroutineScope.launch {
//
//            when (showSnackbar(
//                message = message,
//                duration = duration,
//                actionLabel = action,
//                withDismissAction = duration == Indefinite,
//            )) {
//                SnackbarResult.Dismissed -> onSnackBarDismiss.invoke()
//                SnackbarResult.ActionPerformed -> onSnackBarAction.invoke()
//            }
//        }
//    }
//}


//
//class PermissionHAndler : ComponentActivity() {
//    private val activityResultLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        )
//        { permissions ->
//            // Handle Permission granted/rejected
//            var permissionGranted = true
//            permissions.entries.forEach {
//                if (it.key in MainActivity.REQUIRED_PERMISSIONS && it.value == false)
//                    permissionGranted = false
//            }
//            if (!permissionGranted) {
//                Toast.makeText(
//                    baseContext,
//                    "Permission request denied",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//    fun allPermissionsGranted() = MainActivity.REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//            baseContext, it
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//
//
//    fun myRequestPermissions() {
//        activityResultLauncher.launch(MainActivity.REQUIRED_PERMISSIONS)
//    }
//}