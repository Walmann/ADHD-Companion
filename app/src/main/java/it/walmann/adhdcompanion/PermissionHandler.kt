package it.walmann.adhdcompanion

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat.startActivity


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
        if (context.checkSelfPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_DENIED) {
            Log.e("PermissionHandler", "No ScheduleExactAlarms permission granted")


            val settingsIntent: Intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)


            startActivity(context, settingsIntent, null)

        }

    }
    Log.d("PermissionHandler", "ScheduleExactAlarms permission granted")
    return true
}