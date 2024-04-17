package it.walmann.adhdcompanion.Components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import java.time.LocalDateTime


@Composable
fun TimeSelectDialog(
    dialogTitle: String,
//    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (LocalDateTime) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ){


        Card(
            modifier = Modifier
                .fillMaxWidth()
                //            .height(375.dp)
                .padding(16.dp),
            //        shape = RoundedCornerShape(16.dp),
        ) {
            var selectedDays by remember { mutableIntStateOf(0) }
            var selectedHours by remember { mutableIntStateOf(0) }
            var selectedMinutes by remember { mutableIntStateOf(0) }


            fun calculateTimerTime(): Int {
                val seconds = (86400 * selectedDays) + (60 * selectedHours) + selectedMinutes
                return seconds
            }

//            fun calculateNewTimerTime(): Map<String, String> {
            fun calculateNewTimerTime():LocalDateTime {
                val currentDateTime = LocalDateTime.now()
                val reminderTimeInput = currentDateTime.plusDays(selectedDays.toLong()).plusHours(selectedHours.toLong()).plusMinutes(selectedMinutes.toLong())
//                val localReminderTime = mapOf(
//                    "reminderYear" to "${currentDateTime.year}",
//                    "reminderMonth" to "${currentDateTime.monthValue}",
//                    "reminderDay" to "${currentDateTime.dayOfMonth}",
//                    "reminderHour" to "${currentDateTime.hour}",
//                    "reminderMinute" to "${currentDateTime.minute}",
//                )

//
                return reminderTimeInput
            }
//            fun calculateNewTimerTime(): String {
//                val localtime = LocalDateTime.now().plusDays(selectedDays.toLong()).plusHours(
//                    selectedHours.toLong()
//                ).plusMinutes(selectedMinutes.toLong())
//
//                return localtime.toString()
//            }

//            onValueChange(calculateNewTimerTime())

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
                //                modifier = Modifier
                ////                    .padding(16.dp)
                //                ,
                //            )
                //            Column {
                //                Text(text = "Calculated: ${calculateTimerTime()}")
                //                Text(text = "LocalTime: ${LocalTime.now()}")
                //                Text(text = "New Time: ${calculateNewTimerTime()}")
                //            }
                Row {// TODO Create box around each section. Maybe mark by text.
                    NumberPicker(
                        value = selectedDays,
                        onValueChange = { selectedDays = it },
                        range = 0..100
                    )
                    NumberPicker(
                        value = selectedHours,
                        onValueChange = { selectedHours = it },
                        range = 0..100
                    )
                    NumberPicker(
                        value = selectedMinutes,
                        onValueChange = { selectedMinutes = it },
                        range = 0..100
                    )
                }
                Row {
                    Button(onClick = onDismissRequest) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        onConfirmRequest(calculateNewTimerTime())

                    }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSelectBox(
    dialogTitle: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(375.dp)
            .padding(16.dp),
//        shape = RoundedCornerShape(16.dp),
    ) {
        var selectedDays by remember { mutableIntStateOf(0) }
        var selectedHours by remember { mutableIntStateOf(0) }
        var selectedMinutes by remember { mutableIntStateOf(0) }


        fun calculateTimerTime(): Int {
            val seconds = (86400 * selectedDays) + (60 * selectedHours) + selectedMinutes
            return seconds
        }

        fun calculateNewTimerTime(): String {
            val localtime = LocalDateTime.now().plusDays(selectedDays.toLong()).plusHours(
                selectedHours.toLong()
            ).plusMinutes(selectedMinutes.toLong())

            return localtime.toString()
        }

        onValueChange(calculateNewTimerTime())

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
//                modifier = Modifier
////                    .padding(16.dp)
//                ,
//            )
//            Column {
//                Text(text = "Calculated: ${calculateTimerTime()}")
//                Text(text = "LocalTime: ${LocalTime.now()}")
//                Text(text = "New Time: ${calculateNewTimerTime()}")
//            }
            Row {// TODO Create box around each section. Maybe mark by text.
                NumberPicker(
                    value = selectedDays,
                    onValueChange = { selectedDays = it },
                    range = 0..100
                )
                NumberPicker(
                    value = selectedHours,
                    onValueChange = { selectedHours = it },
                    range = 0..100
                )
                NumberPicker(
                    value = selectedMinutes,
                    onValueChange = { selectedMinutes = it },
                    range = 0..100
                )
            }
        }
    }
}

