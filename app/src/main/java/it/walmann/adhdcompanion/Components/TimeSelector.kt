package it.walmann.adhdcompanion.Components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import java.util.Calendar


@Composable
private fun CancelAndNextButtons(
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit,
) {
    Row {
        Button(onClick = onDismissRequest) {// TODO Create Back Button function. If needed?
            Text(text = "Cancel")
        }
        Button(onClick = {
            onConfirmRequest(
                calculateNewTimerTime(
                    selectedDays = selectedDays,
                    selectedHours = selectedHours,
                    selectedMinutes = selectedMinutes
                )
            )
        }
        ) {
            Text(text = "Save")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectDialog(
    calendar: Calendar,
    dialogTitle: String = "Select Date",
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
//            var selectedDays by remember { mutableIntStateOf(0) }
//            var selectedHours by remember { mutableIntStateOf(0) }
//            var selectedMinutes by remember { mutableIntStateOf(0) }

//            val currTime by remember { mutableStateOf<LocalDateTime>(LocalDateTime.now()) }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                DateSelectDialog(
//                    calendar = calendar,
//                    onConfirmRequest = onConfirmRequest,
//                    onDismissRequest = onDismissRequest
//                )


//                Text(
//                    text = dialogTitle,
//                    modifier = Modifier.padding(16.dp),
//                )
                //            Text( // TODO Add text, "Next reminder in: XX days XX hours xx Minutes.
                //                text = "$selectedDays, $selectedHours, $selectedMinutes",
                //            )

//                TimeSelector(
//                    selectedDays = selectedDays,
//                    selectedHours = selectedHours,
//                    selectedMinutes = selectedMinutes,
//                    onDayChange = { selectedDays = it },
//                    onHourChange = { selectedHours = it },
//                    onMinuteChange = { selectedMinutes = it }
//                )
//                CancelAndNextButtons(
//                    selectedDays = selectedDays,
//                    selectedHours = selectedHours,
//                    selectedMinutes = selectedMinutes,
//                    onConfirmRequest = onConfirmRequest,
//                    onDismissRequest = onDismissRequest
//                )
            }
        }
    }
}


@Composable
fun TimeSelectDialog(
    dialogTitle: String,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            var selectedDays by remember { mutableIntStateOf(0) }
            var selectedHours by remember { mutableIntStateOf(0) } // FIX When adding time, it always start at 0, so if you go to add or subtract time, it will calculate from the reminderTime not actual time.
            var selectedMinutes by remember { mutableIntStateOf(0) }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = dialogTitle,
                    modifier = Modifier.padding(16.dp),
                )
                //            Text( // TODO Add text, "Next reminder in: XX days XX hours xx Minutes.
                //                text = "$selectedDays, $selectedHours, $selectedMinutes",
                //            )

                TimeSelector(
                    selectedDays = selectedDays,
                    selectedHours = selectedHours,
                    selectedMinutes = selectedMinutes,
                    onDayChange = { selectedDays = it },
                    onHourChange = { selectedHours = it },
                    onMinuteChange = { selectedMinutes = it }
                )
                CancelAndNextButtons(
                    selectedDays = selectedDays,
                    selectedHours = selectedHours,
                    selectedMinutes = selectedMinutes,
                    onConfirmRequest = onConfirmRequest,
                    onDismissRequest = onDismissRequest
                )
            }
        }
    }
}

@Composable
private fun TimeSelector(
//    onValueChange: (String) -> Unit
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
) {
    Row {// TODO Create box around each section. Maybe mark by text.
        NumberPicker(
            value = selectedDays,
//            onValueChange = { onDayChange = it },
            onValueChange = onDayChange,
            range = 0..100
        )
        NumberPicker(
            value = selectedHours,
            onValueChange = onHourChange,
            range = 0..100
        )
        NumberPicker(
            value = selectedMinutes,
            onValueChange = onMinuteChange,
            range = 0..100
        )
    }
}


private fun calculateNewTimerTime(
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
): Calendar {

    val currentDateTime = Calendar.getInstance()

    currentDateTime.add(Calendar.DATE, selectedDays)
    currentDateTime.add(Calendar.HOUR, selectedHours)
    currentDateTime.add(Calendar.MINUTE, selectedMinutes)

//    val reminderTimeInput = currentDateTime
//        .plusDays(selectedDays.toLong())
//        .plusHours(selectedHours.toLong())
//        .plusMinutes(selectedMinutes.toLong())
    return currentDateTime
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectDialog(
calendar: Calendar,
onDismissRequest: () -> Unit,
onConfirmRequest: (Calendar) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
//            openDialog.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    calendar.timeInMillis = datePickerState.selectedDateMillis!!
                    onConfirmRequest
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
