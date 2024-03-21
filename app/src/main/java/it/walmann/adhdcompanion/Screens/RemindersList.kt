package it.walmann.adhdcompanion.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.walmann.adhdcompanion.CommonUI.MyScaffolding
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.R

@Composable
fun RemindersScreen(modifier: Modifier, navController: NavController) {
        MyScaffolding(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/
                        navController.navigate(CupcakeScreen.NewReminder.name)
                    },
//                Modifier.background = Color.Red,
                    content = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
//                        tint = Color.White
                        )
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
//            .padding(it)
                    .fillMaxSize()
//            .verticalScroll(rememberScrollState())
                    .background(Color(0xff8d6e63)),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
            ReminderCard(
                ReminderTime = "10:45",
                ReminderDate = "03.02.2024",
                ReminderText = "Lorem Ipsum is simply dummy ",
                modifier = Modifier
            )
        }
    }
}



@Composable
fun ReminderCard(
    ReminderTime: String,
    ReminderDate: String,
    ReminderText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(275.dp)
            .padding(vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
//                .background(Color.Blue)
//                .weight(1f)
                .fillMaxSize()
        ) {
            Row(
//                modifier = modifier
//                .background(Color.Black)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .width(200.dp)
                        .height(200.dp)


                ) {
                    Text(
                        text = ReminderTime,
                        fontSize = 50.sp,
                    )
                    Text(
                        text = ReminderDate,
                        fontSize = 20.sp,
                    )

                }
                Image(
                    modifier = modifier
                        .height(200.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Fit,
                    painter = painterResource(id = R.drawable.placeholder_remindercard_image),
                    contentDescription = null
                )
            }
            Text(
                text = ReminderText,
                fontSize = 20.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp
                    )
            )
        }
    }
}
