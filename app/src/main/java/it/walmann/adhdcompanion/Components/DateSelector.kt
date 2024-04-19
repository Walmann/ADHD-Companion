package it.walmann.adhdcompanion.Components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.window.Dialog
import it.walmann.adhdcompanion.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorDialog( // https://dribbble.com/shots/16612638-Day-Time-Picker-Dark-Mode-Anywhere-DS
    modifier: Modifier = Modifier,
    calendar: Calendar,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (Calendar) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        val datePickState = rememberDatePickerState()
        var showDatePicker = true

        Card(
//            modifier = modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Button(
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(1.dp),
                modifier = modifier.padding(10.dp).fillMaxWidth(),
                onClick = {
                    showDatePicker = !showDatePicker
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.widthIn(min = 200.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_days),
                        contentDescription = null,
                        modifier
                            .padding(10.dp)
                            .size(20.dp)
                    )
                    Column {
                        // "Select a day", and currently chosen date
                        Text(text = "Select a date")
                        Text(
                            text = "${calendar.get(Calendar.YEAR)}.${calendar.get(Calendar.MONTH)}.${calendar.get(Calendar.DATE)}"
                        )
                    }
                }
            }
            if (showDatePicker) {
                DatePicker(state = datePickState)
            }

            // Select Date


            // Select Time

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