package it.walmann.adhdcompanion.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MainActivity

@Composable
fun BlankStartpage(modifier: Modifier = Modifier, reminderUID: String?) {

    if (reminderUID.isNullOrBlank() or reminderUID.isNullOrEmpty()){
        MainActivity.navigator.navigate(CupcakeScreen.ReminderList.name)
    }
    else if (!reminderUID.isNullOrBlank() or !reminderUID.isNullOrEmpty()) {
        MainActivity.navigator.navigate("${CupcakeScreen.ReminderDetails.name}/${reminderUID}")
    } else {
        Text(text = "Error navigating to Reminder.")
    }
}