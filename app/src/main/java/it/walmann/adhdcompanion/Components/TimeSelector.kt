package it.walmann.adhdcompanion.Components


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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TimeSelectorDialog(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = { /*TODO*/ },
    ) {

        TimeSelectWidget()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeSelectWidget(modifier: Modifier = Modifier) {
//    THIS WIDGET IS ABANDONED! I may want to see into this later. But now its stopping me from continuing.
    var rotaryValue by remember {
        mutableFloatStateOf(0f)
    }
    Card(
        modifier = modifier
            .height(500.dp)
            .width(500.dp)
    ) {
        // Create a scroll input with X minutes. Make infinite scroll.
        // Create input for Days, Hours and minutes.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()

        ) {
            Text(text = "hello!")
            ScrollableIntList()
            MaterialNumberPicker() // TODO NEXT Try this module. If not, create your own.

        }

    }
}

@Composable
fun ScrollableIntList(
    IntStart: Int = 0,
    IntStop: Int = 100,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier
            .background(color = Color.Red)
            .height(200.dp)
            .width(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        items(IntStop + 1) { index ->
            Text(text = "$index")
        }
    }
}