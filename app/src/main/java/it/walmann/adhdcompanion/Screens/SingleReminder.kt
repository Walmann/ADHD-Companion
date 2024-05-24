package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import it.walmann.adhdcompanion.Components.DateSelectorDialog
import it.walmann.adhdcompanion.Components.MyButton
import it.walmann.adhdcompanion.Components.MyButtonCombinedHorizontal
import it.walmann.adhdcompanion.Components.QuickReminderTimerDialog
import it.walmann.adhdcompanion.Components.ReminderCard
import it.walmann.adhdcompanion.Components.TimeWheelSelectDialog
import it.walmann.adhdcompanion.Components.getTimeUnitFromInt
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
import it.walmann.adhdcompanion.Handlers.Settings.AppSettings
import it.walmann.adhdcompanion.Handlers.Settings.getAppSetting
import it.walmann.adhdcompanion.Handlers.Settings.setAppSetting
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.MyObjects.reminder
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun SingleReminderForm(
    reminderID: Long,
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val currReminder = MainActivity.reminderDB.ReminderDao().getReminder(reminderID)

    Scaffold(
//        topBar = { MyTopAppBar() },

    ) { innerPadding ->

        SingleReminderForm(
            context = context,
            reminder = currReminder,
            modifier = modifier.padding(innerPadding),
            navController = navController
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleReminderForm(
    context: Context,
    photoUri: Uri = Uri.EMPTY,
    modifier: Modifier = Modifier,
    navController: NavController,
    isBeingInitialized: Boolean = false,
    reminder: reminder = reminder(
        uid = Calendar.getInstance().timeInMillis,
        reminderImage = photoUri.lastPathSegment.toString(),
        reminderImageFullPath = photoUri.toString(),
        reminderCalendar = Calendar.getInstance()
    ),
) {
    /**
     * Screen to show a single Reminder.
     */

    val currentReminder = remember { mutableStateOf(reminder) }


    val openTimerDialog = remember { mutableStateOf(false) }
    val openDateAndTimerDialog = remember { mutableStateOf(false) }
    val openQuickSettings = remember { mutableStateOf(false) }

    val timePState = rememberTimePickerState(
        is24Hour = true,
        initialHour = currentReminder.value.reminderCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentReminder.value.reminderCalendar.get(Calendar.MINUTE)
    )

    val currTimeValue = remember { mutableStateOf("") }
    val currTimeUnit = remember { mutableStateOf("") }


    LaunchedEffect(key1 = context) {
        CoroutineScope(Dispatchers.Main).launch {
            currTimeValue.value = getAppSetting(context, AppSettings.QuickReminderValue).toString()
            currTimeUnit.value = getAppSetting(context, AppSettings.QuickReminderUnit).toString()
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .weight(9f)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReminderCard(
                reminder = currentReminder.value,
                context = context,
                isEditable = true,
                modifier = Modifier,
                onEditTimeClick = { openTimerDialog.value = !openTimerDialog.value },
                onEditDateClick = { openDateAndTimerDialog.value = !openDateAndTimerDialog.value }
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(vertical = 10.dp),
        ) {
            if (isBeingInitialized) {
                MyButtonCombinedHorizontal(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    left_onClick = {
                        currentReminder.value.reminderCalendar.add(Calendar.MINUTE, 10)
                        saveReminder(context, newReminder = currentReminder.value, navController)
                    },
                    left_text = "Remind me in ${currTimeValue.value} ${
                        getTimeUnitFromInt(
                            currTimeUnit.value
                        )
                    }",
                    right_onClick = {
                        openQuickSettings.value = !openQuickSettings.value
                    },
                    right_text = "⚙️"
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            Row(// Cancel and Save buttons
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,

            ) {
                MyButton(
                    text = "Cancel",
                    modifier = Modifier.fillMaxWidth(0.3f),
                    onClick = {},
                )

                MyButton(modifier = Modifier.fillMaxWidth(0.4f), text = "Save", onClick = {
                    currentReminder.value.reminderCalendar.set(
                        Calendar.MINUTE, timePState.minute
                    )
                    currentReminder.value.reminderCalendar.set(
                        Calendar.HOUR_OF_DAY, timePState.hour
                    )
                    saveReminder(context, newReminder = currentReminder.value, navController)
                })
            }
        }



        if (openTimerDialog.value) {
            TimeWheelSelectDialog(
                onDismissRequest = {
                    openTimerDialog.value = false
                    openDateAndTimerDialog.value = false
                },
                confirmButton = {
                    MyButton(
                        onClick = {
                            openTimerDialog.value = false
                            openDateAndTimerDialog.value = false
                        }, text = "Save"
                    )
                },
                dismissButton = { /*TODO*/ },

                ) {
                TimePicker(state = timePState)
            }
        }


        if (openDateAndTimerDialog.value) {
            DateSelectorDialog(onDismissRequest = {
                openTimerDialog.value = false
                openDateAndTimerDialog.value = false
            }, onConfirmRequest = {
                currentReminder.value.reminderCalendar = it
                openTimerDialog.value = false
                openDateAndTimerDialog.value = false
            }, calendar = currentReminder.value.reminderCalendar
            )
        }
        if (openQuickSettings.value) {
            QuickReminderTimerDialog(onConfirm = { selectedUnit, selectedAmount ->
                CoroutineScope(Dispatchers.Main).launch {
                    setAppSetting(
                        context = context, AppSettings.QuickReminderUnit, value = selectedUnit
                    )
                    setAppSetting(
                        context = context,
                        AppSettings.QuickReminderValue,
                        value = selectedAmount
                    )
                }
                CoroutineScope(Dispatchers.Main).launch {
                    currTimeValue.value =
                        getAppSetting(context, AppSettings.QuickReminderValue).toString()
                    currTimeUnit.value =
                        getAppSetting(context, AppSettings.QuickReminderUnit).toString()
                    openQuickSettings.value =
                        !openQuickSettings.value // TODO Make the button update when closing this window.
                }

            })
        }


    }
}


private fun saveReminder(
    context: Context, newReminder: reminder, navController: NavController
) {
    reminderSave(context = context, reminderToSave = newReminder)
    navController.navigate(CupcakeScreen.Start.name)
}


//@PreviewFontScale
//@PreviewScreenSizes
//@PreviewLightDark
//@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420")
//@Preview(widthDp = 680, heightDp = 2000)
@PreviewLightDark
@Composable
private fun NewReminderPreview() {
    val context = LocalContext.current
//    val resources = context.resources
    val photoUri =
        Uri.parse("android.resource://it.walmann.adhdcompanion/" + R.drawable.placeholder_reminderimage)
//    ADHDCompanionTheme {
        Surface {
            SingleReminderForm(
                context = context,
                photoUri = photoUri,
                isBeingInitialized = true,
                modifier = Modifier,
                navController = rememberNavController()
            )
        }
//    }
}


//package it.walmann.adhdcompanion.Screens
//
//import android.content.Context
//import android.net.Uri
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TimePicker
//import androidx.compose.material3.rememberTimePickerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewFontScale
//import androidx.compose.ui.unit.dp
//import androidx.core.net.toUri
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.rememberAsyncImagePainter
//import it.walmann.adhdcompanion.Components.DateSelectorDialog
//import it.walmann.adhdcompanion.Components.MyButtonCombinedBottom
//import it.walmann.adhdcompanion.Components.MyButtonCombinedTop
//import it.walmann.adhdcompanion.Components.TimeWheelSelectDialog
//import it.walmann.adhdcompanion.Components.MyButton
//import it.walmann.adhdcompanion.Components.MyButtonCombinedHorizontal
//import it.walmann.adhdcompanion.Components.QuickReminderTimerDialog
//import it.walmann.adhdcompanion.Components.getTimeUnitFromInt
//import it.walmann.adhdcompanion.CupcakeScreen
//import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
//import it.walmann.adhdcompanion.Handlers.Settings.AppSettings
//import it.walmann.adhdcompanion.Handlers.Settings.getAppSetting
//import it.walmann.adhdcompanion.Handlers.Settings.setAppSetting
//import it.walmann.adhdcompanion.MainActivity
//import it.walmann.adhdcompanion.MyObjects.reminder
//import it.walmann.adhdcompanion.R
//import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//
//@Composable
//fun SingleReminderForm(
//    reminderID: Long,
//    context: Context,
//    modifier: Modifier = Modifier,
//    navController: NavController = rememberNavController(),
//) {
//    val currReminder = MainActivity.reminderDB.ReminderDao().getReminder(reminderID)
//
//    Scaffold(
////        topBar = { MyTopAppBar() },
//
//    ) { innerPadding ->
//
//        SingleReminderForm(
//            context = context,
//            reminder = currReminder,
//            modifier = modifier.padding(innerPadding),
//            navController = navController
//        )
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SingleReminderForm(
//    context: Context,
//    photoUri: Uri = Uri.EMPTY,
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    reminder: reminder = reminder(
//        uid = Calendar.getInstance().timeInMillis,
//        reminderImage = photoUri.lastPathSegment.toString(),
//        reminderImageFullPath = photoUri.toString(),
//        reminderCalendar = Calendar.getInstance()
//    ),
//) {
//    /**
//     * Screen to show a single Reminder.
//     */
//
//    val currentReminder = remember { mutableStateOf(reminder) }
//
//    val currPhotoUri = if (photoUri == Uri.EMPTY) {
//        reminder.reminderImageFullPath.toUri()
//    } else {
//        photoUri
//    }
//
//
//    val openTimerDialog = remember { mutableStateOf(false) }
//    val openDateAndTimerDialog = remember { mutableStateOf(false) }
//    val openQuickSettings = remember { mutableStateOf(false) }
//
//    val timePState = rememberTimePickerState(
//        is24Hour = true,
//        initialHour = currentReminder.value.reminderCalendar.get(Calendar.HOUR_OF_DAY),
//        initialMinute = currentReminder.value.reminderCalendar.get(Calendar.MINUTE)
//    )
//
//    val currTimeValue = remember { mutableStateOf("") }
//    val currTimeUnit = remember { mutableStateOf("") }
//
//
////    LaunchedEffect(key1 = context) {
//    CoroutineScope(Dispatchers.Main).launch {
//        currTimeValue.value = getAppSetting(context, AppSettings.QuickReminderValue).toString()
//        currTimeUnit.value = getAppSetting(context, AppSettings.QuickReminderUnit).toString()
//    }
//
//
//    Column(
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//    ) {
////        Text(text = formatTime(currentReminder.component1().reminderCalendar))
//        Text(text = "Remind me in ${currTimeValue.value} ${getTimeUnitFromInt(currTimeUnit.value)}")
//        Image(
//            painter = rememberAsyncImagePainter(currPhotoUri),
//            contentDescription = null,
//            contentScale = ContentScale.Inside,
//            modifier = modifier
//                .weight(7f)
//                .padding(vertical = 10.dp)
//
//        )
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween,
//            modifier = modifier
//                .weight(10f)
//
//        ) {
//            MyButtonCombinedTop(
//                onClick = {
//                    openTimerDialog.value = !openDateAndTimerDialog.value
//                },
////                text = "${currentReminder.reminderCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')}:${currentReminder.reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}",
//                text = formatTime(currentReminder.value.reminderCalendar),
//                textStyle = MaterialTheme.typography.displayLarge,
//                modifier = Modifier
//                    .weight(10f)
//                    .fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(1.dp))
//            MyButtonCombinedBottom(
//                onClick = {
//                    openDateAndTimerDialog.value = !openDateAndTimerDialog.value
//                },
//                modifier = Modifier
//                    .weight(10f)
//                    .fillMaxWidth(),
//                textStyle = MaterialTheme.typography.displayMedium,
//                text = formatDate(currentReminder.value.reminderCalendar)
//            )
//            Spacer(modifier = Modifier.height(30.dp))
//            MyButtonCombinedHorizontal(
//                modifier = Modifier.fillMaxWidth(),
//                left_onClick = {
//                    currentReminder.value.reminderCalendar.add(Calendar.MINUTE, 10)
//                    saveReminder(context, newReminder = currentReminder.value, navController)
//                },
//                left_text = "Remind me in ${currTimeValue.value} ${getTimeUnitFromInt(currTimeUnit.value)}",
//                right_onClick = {
//
//                    openQuickSettings.value = !openQuickSettings.value
//
//                },
//                right_text = "⚙️"
//            )
//
//            Spacer(modifier = Modifier.height(30.dp))
//            MyButton(
//                onClick = {
//                    val repeats = 10
//                    repeat(repeats) {
//
//                        currentReminder.value.reminderCalendar.add(Calendar.MINUTE, repeats)
//                        currentReminder.value.uid =
//                            currentReminder.value.reminderCalendar.timeInMillis
//                        saveReminder(
//                            context, newReminder = currentReminder.value, navController
//                        )
//                    }
//                },
//                text = "Create 10 copies",
//                textStyle = MaterialTheme.typography.headlineSmall
//            )
//
//
//            if (openTimerDialog.value) {
//                TimeWheelSelectDialog(
//                    onDismissRequest = {
//                        openTimerDialog.value = false
//                        openDateAndTimerDialog.value = false
//                    },
//                    confirmButton = {
//                        MyButton(
//                            onClick = {
//                                openTimerDialog.value = false
//                                openDateAndTimerDialog.value = false
//                            },
//                            text = "Save"
//                        )
//                    },
//                    dismissButton = { /*TODO*/ },
//
//                    ) {
//                    TimePicker(state = timePState)
//                }
//            }
//
//
//            if (openDateAndTimerDialog.value) {
//                DateSelectorDialog(
//                    onDismissRequest = {
//                        openTimerDialog.value = false
//                        openDateAndTimerDialog.value = false
//                    }, onConfirmRequest = {
//                        currentReminder.value.reminderCalendar = it
//                        openTimerDialog.value = false
//                        openDateAndTimerDialog.value = false
//                    },
//                    calendar = currentReminder.value.reminderCalendar
//                )
//            }
//            if (openQuickSettings.value) {
//                QuickReminderTimerDialog(onConfirm = { selectedUnit, selectedAmount ->
//                    CoroutineScope(Dispatchers.Main).launch {
//                        setAppSetting(
//                            context = context,
//                            AppSettings.QuickReminderUnit,
//                            value = selectedUnit
//                        )
//                        setAppSetting(
//                            context = context,
//                            AppSettings.QuickReminderValue,
//                            value = selectedAmount
//                        )
//                    }
//                    CoroutineScope(Dispatchers.Main).launch {
//                        currTimeValue.value =
//                            getAppSetting(context, AppSettings.QuickReminderValue).toString()
//                        currTimeUnit.value =
//                            getAppSetting(context, AppSettings.QuickReminderUnit).toString()
//                        openQuickSettings.value =
//                            !openQuickSettings.value // TODO Make the button update when closing this window.
//                    }
//
//                })
//            }
//
//
//            Row(
//                verticalAlignment = Alignment.Bottom,
//                horizontalArrangement = Arrangement.SpaceAround,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .weight(10f)
//                    .padding(vertical = 10.dp),
//
//                ) {
//                MyButton(
//                    text = "Cancel",
//                    modifier = Modifier.fillMaxWidth(0.3f),
//                    onClick = {},
//                )
//
//                MyButton(
//                    modifier = Modifier.fillMaxWidth(0.4f),
//                    text = "Save",
//                    onClick = {
//                        currentReminder.value.reminderCalendar.set(
//                            Calendar.MINUTE,
//                            timePState.minute
//                        )
//                        currentReminder.value.reminderCalendar.set(
//                            Calendar.HOUR_OF_DAY,
//                            timePState.hour
//                        )
//                        saveReminder(context, newReminder = currentReminder.value, navController)
//                    })
//            }
//        }
//    }
//}
//
//private fun saveReminder(
//    context: Context,
//    newReminder: reminder,
//    navController: NavController
//) {
//    reminderSave(context = context, reminderToSave = newReminder)
//    navController.navigate(CupcakeScreen.Start.name)
//}
//
//private fun formatTime(reminderCalendar: Calendar): String {
//    val timeString = "${
//        reminderCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
//    }:${reminderCalendar.get(Calendar.MINUTE).toString().padStart(2, '0')}"
//    return timeString
//}
//
//private fun formatDate(reminderCalendar: Calendar): String {
//    val dateString = "${
//        reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')
//    }.${
//        reminderCalendar.get(Calendar.MONTH).toString().padStart(2, '0')
//    }.${
//        reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')
//    }"
//    return dateString
//}
//
//@PreviewFontScale
////@PreviewScreenSizes
////@PreviewLightDark
//@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420")
////@Preview(widthDp = 680, heightDp = 2000)
//@Composable
//private fun NewReminderPreview() {
//    val context = LocalContext.current
////    val resources = context.resources
//    val photoUri =
//        Uri.parse("android.resource://it.walmann.adhdcompanion/" + R.drawable.placeholder_reminderimage)
//    ADHDCompanionTheme {
//        Surface {
//            SingleReminderForm(
//                context = context,
//                photoUri = photoUri,
//                modifier = Modifier,
//                navController = rememberNavController()
//            )
//        }
//    }
//}