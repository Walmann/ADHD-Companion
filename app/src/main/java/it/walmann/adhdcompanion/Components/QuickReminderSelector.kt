package it.walmann.adhdcompanion.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickReminderTimerDialog(
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    modifier: Modifier = Modifier.fillMaxSize(0.8f)
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
        Column {
            Text(text = "Set quick reminder to ")
            Row {
//                TextField(
//                    value = "11",
//                    onValueChange = { selectedAmount = it },
//                    modifier = Modifier
//                )
                MyTextInputNumbersOnly(userValue = { selectedAmount = it })
                MyDropdown(modifier = modifier, itemUnits, onSelectItem = { selectedIndex = it })
            }
        }
    }


}

@Composable
fun MyTextInputNumbersOnly(
    modifier: Modifier = Modifier,
//    userInput: Int,
    userValue: (Int) -> Unit = {}
) {
    val pattern = remember { Regex("^\\d+\$") }

    val text = remember {mutableStateOf("11")}
    Text(text = text.toString())
    TextField(
        value = text,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
//                userInput2 = it as Int
                userValue(text.toInt())
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
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
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = itemUnits[0][0].toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                itemUnits.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item[0].toString()) },
                        onClick = {
                            selectedIndex = item[1] as Int
                            onSelectItem(selectedIndex)
                            expanded = false
                        }
                    )
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