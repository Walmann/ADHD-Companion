package it.walmann.adhdcompanion.Components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.roundToInt


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

    selectedDays: Int,
    selectedHours: Int,
    selectedMinutes: Int,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,

//    stateMinutes: MutableState,
//    stateHours: MutableState,
//    stateDays: MutableState<Int>
) {
    Row {// TODO Create box around each section. Maybe mark by text.
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
                range = 0..100,
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

