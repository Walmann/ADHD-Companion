package it.walmann.adhdcompanion.Screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MainActivity

@Composable
fun BlankStartpage(
    modifier: Modifier = Modifier,
    reminderUID: String?,
    createNewReminder: Boolean = false
) {
    Log.d("Navigator", "Current navigation info: ReinderUID: ${reminderUID} | createNewReminder: ${createNewReminder.toString()}")
    if (createNewReminder) {
        Log.d("Navigator", "Going to NewReminder!")
        MainActivity.navigator.navigate(CupcakeScreen.NewReminder.name)
    } else if (reminderUID.isNullOrBlank() or reminderUID.isNullOrEmpty()) {
        MainActivity.navigator.navigate(CupcakeScreen.ReminderList.name)
    } else if (!reminderUID.isNullOrBlank() or !reminderUID.isNullOrEmpty()) {
        MainActivity.navigator.navigate("${CupcakeScreen.ReminderDetails.name}/${reminderUID}")
    } else {
        Text(text = "Error navigating to Reminder.")
    }
}