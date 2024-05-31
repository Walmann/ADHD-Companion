package it.walmann.adhdcompanion.Components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorDialog(
    modifier: Modifier = Modifier,
    calendar: Calendar,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit = {}
) {
    val datePickState =
        rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis(),
            initialDisplayMode = DisplayMode.Picker,

            )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                val cal2 = Calendar.getInstance()
                val selectedDate = datePickState.selectedDateMillis ?: cal2.timeInMillis
                cal2.setTimeInMillis(selectedDate)

                calendar.set(Calendar.YEAR, cal2.get(Calendar.YEAR))
                calendar.set(Calendar.MONTH, cal2.get(Calendar.MONTH))
                calendar.set(Calendar.DAY_OF_MONTH, cal2.get(Calendar.DAY_OF_MONTH))

                onConfirmRequest(calendar)
            }) {
                Text(text = "Save")
            }
        }
    ) {
        DatePicker(
            state = datePickState,
        )
    }
}

@Preview(name = "Default",widthDp = 500, heightDp = 1000)
@Composable
private fun DateSelectorPreview() {
    DateSelectorDialog(
        calendar = Calendar.getInstance(),
    )
}

@PreviewScreenSizes()
@Composable
private fun DateSelectorPreviewScreenSize() {
    DateSelectorDialog(
        calendar = Calendar.getInstance(),
    )
}
