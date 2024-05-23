package it.walmann.adhdcompanion.Components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.Calendar


class TimeUnits() {
    companion object {
        @Stable
        val Second = listOf("Second", Calendar.SECOND)
        val Minutes = listOf("Minutes", Calendar.MINUTE)
        val Hours = listOf("Hours", Calendar.HOUR_OF_DAY)
        val Days = listOf("Days", Calendar.DAY_OF_YEAR)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickReminderTimerDialog(
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    modifier: Modifier = Modifier.fillMaxSize(0.8f),
    onConfirm: () -> Unit = {}
) {
//    var expanded by remember { mutableStateOf(false) }
    val itemUnits = listOf(
        listOf("Seconds", Calendar.SECOND),
        listOf("Minutes", Calendar.MINUTE),
        listOf("Hours", Calendar.HOUR_OF_DAY),
        listOf("Years", Calendar.DAY_OF_YEAR)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedAmount by remember { mutableIntStateOf(10) }
    Dialog(
        onDismissRequest = { /*TODO*/ },
    )
//        modifier = Modifier
//        .fillMaxSize()
//        .wrapContentSize(Alignment.TopStart))
    {
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
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Set quick reminder to ",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                DiagEntry(text = "Amount") {

                    MyTextInputNumbersOnly(userValue = { selectedAmount = it })
                }
                Spacer(modifier = Modifier.height(5.dp))
                DiagEntry(text = "Units") {

                    MyDropdown(modifier = modifier.width(3.dp),
                        itemUnits,
                        onSelectItem = { selectedIndex = it })

                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    TextButton(
                        onClick = onConfirm
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun DiagEntry(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
//            .fillMaxHeight()
                .weight(5f)
        )
        Box(modifier = modifier.weight(5f, fill = false)) {
            content()
        }
    }
}


@Composable
fun MyTextInputNumbersOnly(
    modifier: Modifier = Modifier,
//    userInput: Int,
    userValue: (Int) -> Unit
) {
    val pattern = remember { Regex("^\\d+\$") }

    var timeAmount by remember { mutableStateOf(TextFieldValue("10")) }
    TextField(value = timeAmount,
//        label = { Text(text = timeAmount.text) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { it ->
            if (it.text.isEmpty() || it.text.matches(pattern)) {
                timeAmount = it
                userValue(it.text.toInt())
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyDropdown(
    modifier: Modifier = Modifier,
    itemUnits: List<List<Any>>,
    onSelectItem: (Int) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val text = remember {
        mutableStateOf(itemUnits[0][0].toString())
    }
    Box(
//        modifier = modifier.fillMaxWidth()
//            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
//            modifier = Modifier.fillMaxSize(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = text.value, // TODO Fetch current from settings
                singleLine = true,
                onValueChange = { itemUnits[selectedIndex][1] },
                readOnly = true,
                trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                itemUnits.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item[0].toString(), overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            selectedIndex = item[1] as Int
                            text.value = item[0].toString()
                            onSelectItem(selectedIndex)
                            expanded = false
                        })
                }
            }
        }
    }
}


@Preview(name = "Default", widthDp = 500, heightDp = 1000)

@Composable
private fun QuickReminderTimerDialogPreview() {

    Surface {
        QuickReminderTimerDialog()
    }


}