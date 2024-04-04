package it.walmann.adhdcompanion.Components


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.chargemap.compose.numberpicker.NumberPicker
import it.walmann.adhdcompanion.CommonUI.MyButtonsAccept

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TimeSelectDialogBox(
//    onDismissRequest: () -> Unit,
//    onConfirmation: () -> Unit,
//    dialogTitle: String,
//) {
//    Dialog(
//        onDismissRequest = { onDismissRequest() }
//    ) {
//        TimeSelectBox(
//            onDismissRequest = onDismissRequest,
//            onConfirmation = onConfirmation,
//            dialogTitle = dialogTitle
//        )
//
//
//    }
//}

@Composable
fun TimeSelectBox(
    dialogTitle: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(375.dp)
            .padding(16.dp),
//        shape = RoundedCornerShape(16.dp),
    ) {
        var selectedDays by remember { mutableStateOf(0) }
        var selectedHours by remember { mutableStateOf(0) }
        var selectedMinutes by remember { mutableStateOf(0) }


        Column(
            modifier = Modifier
//                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = dialogTitle,
                modifier = Modifier.padding(16.dp),
            )
            Text( // TODO Add text, "Next reminder in: XX days XX hours xx Minutes.
                text = "$selectedDays, $selectedHours, $selectedMinutes",
                modifier = Modifier
//                    .padding(16.dp)
                ,
            )
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