package it.walmann.adhdcompanion

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import it.walmann.adhdcompanion.MyObjects.simpleAlert


//@Composable
//fun requestPermission(permission: String, context: Context = LocalContext.current): Boolean {
//    val permissionState = remember { mutableStateOf(false) }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        permissionState.value = isGranted
//    }
//
//    LaunchedEffect(key1 = true) {
//        val hasPermission = ContextCompat.checkSelfPermission(
//            context,
//            permission
//        ) == PackageManager.PERMISSION_GRANTED
//        if (!hasPermission) {
//            if (shouldShowRequestPermissionRationale(context as Activity, permission)) {
//                // Show explanation to user why permission is needed
//            }
//            launcher.launch(permission)
//        } else {
//            permissionState.value = true
//        }
//    }
//
////    // Check for permanently denied state (Android 13+)
////    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
////        val shouldShowRationale = shouldShowRequestPermissionRationale(context as Activity, permission)
////        if (!permissionState.value && !shouldShowRationale) {
////            simpleAlert(message = "Permission error. Go to app settings and allow Notifications.", context = context) // Permission permanently denied, maybe show a snackbar with instructions to app settings
////        }
////    }
//
//    return permissionState.value
//}



//@Composable
//fun requestPermission(permission: String, context: Context = LocalContext.current): Boolean {
//    val permissionState = remember { mutableStateOf(false) }
//
//
//    if (permission == "android.permission.CAMERA") {
//        permissionState.value = requestPermissionCamera(context)
//    }
//    else if (permission == "android.permission.SCHEDULE_EXACT_ALARM") {
//        permissionState.value = requestPermissionExactAlarm(context)
//    }
//
//
//    return permissionState.value
//
////    val permissionState = remember { mutableStateOf(false) }
////    val launcher = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.RequestPermission()
////    ) { isGranted ->
////        permissionState.value = isGranted
////    }
////
////    LaunchedEffect(key1 = true) {
////        val hasPermission = ContextCompat.checkSelfPermission(
////            context,
////            permission
////        ) == PackageManager.PERMISSION_GRANTED
////        if (!hasPermission) {
////            launcher.launch(permission)
////        } else {
////            permissionState.value = true
////        }
////    }
////
////    return permissionState.value
//}


@Composable
fun requestPermissionCamera(context: Context): Boolean {
    val permissionState = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionState.value = isGranted
    }
    LaunchedEffect(key1 = true) {
        val hasPermission = context.checkSelfPermission(
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            launcher.launch(android.Manifest.permission.CAMERA)
        } else {
            permissionState.value = true
        }
    }

    return permissionState.value
}

fun requestPermissionExactAlarm(context: Context, aManager: AlarmManager): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!aManager.canScheduleExactAlarms()) {
            val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            startActivity(context, settingsIntent, null)
        } else {
            Log.d("MainActivity", "onCreate: can schedule it")
        }
    }
    return true
}

@Composable
fun requestPermissionNotifications(context: Context): Boolean {
    val permissionState = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionState.value = isGranted
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    LaunchedEffect(key1 = true) {
        val hasPermission =
            context.checkSelfPermission(
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            permissionState.value = true
        }
    } }

    return permissionState.value
}