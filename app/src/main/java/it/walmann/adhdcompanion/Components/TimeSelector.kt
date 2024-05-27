package it.walmann.adhdcompanion.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.NumberPicker
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeWheelSelectDialog(
    modifier: Modifier = Modifier,
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
//    timePickerState: TimePickerState,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                
            ) {
                Text(
                    modifier = Modifier
//                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                    ,
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}


@Composable
fun TimeSlotsSelectDialog(
    dialogTitle: String,
    calendar: Calendar,
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
            var selectedHours by remember { mutableIntStateOf(0) }
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


                TimeSlotsSelector(
                    selectedDays = selectedDays,
                    selectedHours = selectedHours,
                    selectedMinutes = selectedMinutes,
                    onDayChange = { selectedDays = it },
                    onHourChange = { selectedHours = it },
                    onMinuteChange = { selectedMinutes = it }
                )
                CancelAndNextButtons(
                    calendar = calendar,
                    selectedDays = selectedDays,
                    selectedHours = selectedHours,
                    selectedMinutes = selectedMinutes,
                    onConfirmRequest = onConfirmRequest,
                    onDismissRequest = onDismissRequest,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}

@Composable
private fun TimeSlotsSelector(
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
) {
    Row {
        val numberPickerModifier = Modifier
            .background(MaterialTheme.colorScheme.onSurface)
//            .height(30.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Day")
            NumberPicker(
                value = selectedDays,
                onValueChange = onDayChange,
                range = 0..100,
                modifier = numberPickerModifier
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hour")
            NumberPicker(
                value = selectedHours,
                onValueChange = onHourChange,
                range = -100..100,
                modifier = numberPickerModifier
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Minute")
            NumberPicker(
                value = selectedMinutes,
                onValueChange = onMinuteChange,
                range = 0..100,
                modifier = numberPickerModifier
            )
        }
    }
}

@Composable
private fun CancelAndNextButtons(
    modifier: Modifier = Modifier,
    calendar: Calendar,
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth(),

        ) {
        Button(onClick = onDismissRequest) {
            Text(text = "Cancel")
        }
//        Spacer(modifier = Modifier.padding(5.dp))
        Button(onClick = {
            onConfirmRequest(
                calculateNewTimerTime(
                    calendar = calendar,
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

private fun calculateNewTimerTime(
    calendar: Calendar,
    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
): Calendar {

//    val currentDateTime = Calendar.getInstance()
    val currentDateTime = calendar

    currentDateTime.add(Calendar.DATE, selectedDays)
    currentDateTime.add(Calendar.HOUR, selectedHours)
    currentDateTime.add(Calendar.MINUTE, selectedMinutes)

    return currentDateTime
}


@OptIn(ExperimentalMaterial3Api::class)
//@PreviewFontScale
@Preview
@Composable
fun TimeSelectDialogPreview() {
    ADHDCompanionTheme {
        Surface {
            TimeWheelSelectDialog(
                onDismissRequest = {                },
                confirmButton = {
                    MyButton(
                        onClick = {                        },
                        text = "Save"
                    )
                },
                dismissButton = {},

                ) {
                TimePicker(
                    state = rememberTimePickerState(
                        is24Hour = true,
                        initialHour = 6,
                        initialMinute = 30
                    )
                )

            }
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DateSelectDialog(
//    calendar: Calendar,
//    onDismissRequest: () -> Unit,
//    onConfirmRequest: (Calendar) -> Unit
//) {
//    val datePickerState = rememberDatePickerState()
//    val confirmEnabled = remember {
//        derivedStateOf { datePickerState.selectedDateMillis != null }
//    }
//    DatePickerDialog(
//        onDismissRequest = {
//            // Dismiss the dialog when the user clicks outside the dialog or on the back
//            // button. If you want to disable that functionality, simply use an empty
//            // onDismissRequest.
////            openDialog.value = false
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    calendar.timeInMillis = datePickerState.selectedDateMillis!!
//                    onConfirmRequest
//                },
//                enabled = confirmEnabled.value
//            ) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = onDismissRequest
//            ) {
//                Text("Cancel")
//            }
//        }
//    ) {
//        DatePicker(state = datePickerState)
//    }
//}
//

