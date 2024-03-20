package it.walmann.adhdcompanion.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.walmann.adhdcompanion.Components.MyCamera
import it.walmann.adhdcompanion.R

@Composable
fun NewReminder(modifier: Modifier) {
    Column(
        modifier = Modifier
//            .padding(it)
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
            .background(Color(0xff8d6e63)),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyCamera()
//        Column(
//        ) {
//            ReminderCard(
//                ReminderTime = "10:45",
//                ReminderDate = "03.02.2024",
//                ReminderText = "Lorem Ipsum is simply dummy ",
//                modifier = Modifier
//            )
//            ReminderCard(
//                ReminderTime = "10:45",
//                ReminderDate = "03.02.2024",
//                ReminderText = "Lorem Ipsum is simply dummy ",
//                modifier = Modifier
//            )
//
//        }

    }
}

