package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import it.walmann.adhdcompanion.Components.DateSelectorDialog
import it.walmann.adhdcompanion.Components.MyButtonCombinedBottom
import it.walmann.adhdcompanion.Components.TimeSelectDialog
import it.walmann.adhdcompanion.Components.MyButtonCombinedTop
import it.walmann.adhdcompanion.Components.myButtonDefault
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.Handlers.Reminder.reminderLoad
import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import java.util.Calendar


@Composable
fun SingleReminderForm(
    calendarId: Long,
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val reminderToLoad = reminderLoad.single(reminderId = calendarId, context = context)
    val photoUri = reminderToLoad.reminderImage

    SingleReminderForm(
        context = context,
        photoUri = photoUri,
        modifier = modifier,
        navController = navController
    )

}


@Composable
fun SingleReminderForm(
    context: Context,
    photoUri: Uri,
    calendarId: String? = null,
    modifier: Modifier,
    navController: NavController,
) {
    /**
     * Screen to show a single Reminder.
     */
    val currentReminder = myReminder(reminderImage = photoUri)


//    var reminderCalendar by remember { mutableStateOf<Calendar>(Calendar.getInstance()) }


//    val newReminder by remember { mutableStateOf(myReminder(reminderCalendar = reminderCalendar)) }
//    newReminder.reminderCalendar = reminderCalendar
//    newReminder.reminderImage = photoUri

    val openTimerDialog = remember { mutableStateOf(false) }
    val openDateAndTimerDialog = remember { mutableStateOf(false) }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Image(
            painter = rememberAsyncImagePainter(photoUri),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier
                .weight(7f)
                .padding(vertical = 10.dp)

        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .weight(10f)

        ) {
            MyButtonCombinedTop(
                onClick = {
                    openTimerDialog.value = !openDateAndTimerDialog.value
                },
                text = "${
                    currentReminder.reminderCalendar.get(Calendar.HOUR_OF_DAY).toString()
                        .padStart(2, '0')
                }:${
                    currentReminder.reminderCalendar.get(Calendar.MINUTE).toString()
                        .padStart(2, '0')
                }",
//                text = "${
//                    reminderCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
//                }:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",
                textStyle = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(1.dp))
            MyButtonCombinedBottom(
                onClick = {
                    openDateAndTimerDialog.value = !openDateAndTimerDialog.value
                },
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.displayMedium,
                text = "${
                    currentReminder.reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')
                }.${
                    currentReminder.reminderCalendar.get(Calendar.MONTH).toString().padStart(2, '0')
                }.${
                    currentReminder.reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')
                }"
            )
            Spacer(modifier = Modifier.height(30.dp))
            myButtonDefault(
                onClick = {
                    currentReminder.reminderCalendar.add(Calendar.MINUTE, 10)
                    saveReminder(context, newReminder = currentReminder, navController)
                },
                text = "⏱\uFE0F Remind me in 10 minutes",
                textStyle = MaterialTheme.typography.headlineSmall
            ) // TODO SETTINGS Make this configurable in settings

            Spacer(modifier = Modifier.height(1.dp))
            myButtonDefault(
                onClick = {
                    currentReminder.reminderCalendar.add(Calendar.SECOND, 10)
                    saveReminder(context, newReminder = currentReminder, navController)
                },
                text = "⏱️ Remind me in 10 seconds",
                textStyle = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(1.dp))
            myButtonDefault(
                onClick = {
                    currentReminder.reminderCalendar.add(Calendar.SECOND, 20)
                    saveReminder(context, newReminder = currentReminder, navController)
                },
                text = "⏱️ Remind me in 20 seconds",
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(1.dp))



            if (openTimerDialog.value) {
                TimeSelectDialog(
                    // TODO Make this prettier
                    dialogTitle = "Select time until next reminder",
                    onConfirmRequest = {
                        currentReminder.reminderCalendar = it
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                    onDismissRequest = {
                        openTimerDialog.value = false
                        openDateAndTimerDialog.value = false
                    },
                )
            }


            if (openDateAndTimerDialog.value) {
                DateSelectorDialog(onDismissRequest = {
                    openTimerDialog.value = false
                    openDateAndTimerDialog.value = false
                }, onConfirmRequest = {
                    currentReminder.reminderCalendar = it
                    openTimerDialog.value = false
                    openDateAndTimerDialog.value = false
                }, calendar = currentReminder.reminderCalendar
                )
            }


            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(10f)
                    .padding(vertical = 10.dp),

                ) {
                NavigationButtons(
                    text = "Cancel",
                    onClick = {},
                )
                NavigationButtons(text = "Save", onClick = {
                    saveReminder(context, newReminder = currentReminder, navController)
//                    saveReminder(context, reminderCalendar, newReminder, navController)
                })
            }
        }
    }
}

@Composable
fun NavigationButtons(
    modifier: Modifier = Modifier, text: String = "Text", onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick, modifier.height(50.dp)
    ) {
//        AutoResizeText(
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}


private fun saveReminder(
    context: Context,
//    reminderCalendar: Calendar,
    newReminder: myReminder,
    navController: NavController
) {
    reminderSave(context = context, reminderToSave = newReminder)
    navController.navigate(CupcakeScreen.Start.name)
}


//@PreviewFontScale
//@PreviewScreenSizes
//@PreviewLightDark
@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420")
//@Preview(widthDp = 680, heightDp = 2000)
@Composable
private fun NewReminderPreview() {
    val context = LocalContext.current
//    val resources = context.resources
    val photoUri =
        Uri.parse("android.resource://it.walmann.adhdcompanion/" + R.drawable.placeholder_reminderimage)
    ADHDCompanionTheme {
        Surface {
            SingleReminderForm(
                context = context,
                photoUri = photoUri,
                modifier = Modifier,
                navController = rememberNavController()
            )
        }
    }
}