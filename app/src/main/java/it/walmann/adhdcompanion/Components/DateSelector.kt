package it.walmann.adhdcompanion.Components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import it.walmann.adhdcompanion.R
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorDialog(
    modifier: Modifier = Modifier,
    calendar: Calendar,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        val datePickState =
            rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())//LocalDateTime.now().toInstant().toEpochMilli())
//        var showDatePicker = true

        Card(
//            modifier = modifier.fillMaxWidth()
        ) {
//
            DatePicker(state = datePickState) // TODO FIX In DatePicker you can see part of the dates for the next month.

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, end = 10.dp)
            ) {
                Button(onClick = { onDismissRequest }) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    val cal2 = Calendar.getInstance()
                    cal2.setTimeInMillis(datePickState.selectedDateMillis!!)

                    calendar.set(Calendar.YEAR, cal2.get(Calendar.YEAR))
                    calendar.set(Calendar.MONTH, cal2.get(Calendar.MONTH))
                    calendar.set(Calendar.DAY_OF_MONTH, cal2.get(Calendar.DAY_OF_MONTH))

                    onConfirmRequest(calendar)
                }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 500, heightDp = 1000)
@Composable
private fun DateSelectorPreview() {
    DateSelectorDialog(
        calendar = Calendar.getInstance(),
//        modifier = Modifier.fillMaxWidth()
    )
}