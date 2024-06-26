package it.walmann.adhdcompanion.Screens


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.walmann.adhdcompanion.Components.ReminderCard
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.MyObjects.debugGetDebugReminders
import java.util.Calendar

@Composable
fun RemindersScreen(
    modifier: Modifier,
    context: Context
) {


    // TODO FUTURE I want to create a better looking app. Therefore the list needs a redesign.
    // Look at these links for inspiration:
    // https://dribbble.com/shots/5221219-Card-Vault-Mobile-App
    // https://dribbble.com/shots/12078609-Circle-Hook-Fisher-man-app-Micro-interactions

    Scaffold(
//        topBar = { MyTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    MainActivity.navigator.navigate(CupcakeScreen.NewReminder.name)
                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Create new reminder",
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val currentDateTime = Calendar.getInstance().timeInMillis

            val reminderArr = if (LocalInspectionMode.current) {
                debugGetDebugReminders(10)
            } else MainActivity.reminderDB.ReminderDao().getAll()
            if (reminderArr.isEmpty()) {
                CreateReminderInstructions(modifier = modifier.padding(20.dp))
            } else {
                Text(text = "Reminders", style = MaterialTheme.typography.displayLarge)

                reminderArr.sortedBy { it.uid }.reversed()
                    .forEach { currReminder ->// (key, value) ->
                        val isExpired =
                            if (currentDateTime >= currReminder.reminderCalendar.timeInMillis) {
                                true
                            } else {
                                false
                            }


                        ReminderCard(
                            modifier = Modifier.alpha(if (isExpired) 0.7f else 1f),
                            reminder = currReminder,
                            isNoteScrollable = false,
                            context = context,
                            onClick = { MainActivity.navigator.navigate("${CupcakeScreen.ReminderDetails.name}/${currReminder.uid}") }
                        )
                    }
            }
        }
    }
}


@Composable
fun CreateReminderInstructions(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "To start creating new reminders, press '+' in the lower right corner")
    }
}


//@Preview
//@PreviewScreenSizes
@Preview
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun RemindersScreenPreview() {
    RemindersScreen(
        modifier = Modifier,
        context = LocalContext.current
    )
}