package it.walmann.adhdcompanion.Screens

import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.Components.RotaryDialDialog
import it.walmann.adhdcompanion.Components.RotaryDialWidget
//import it.walmann.adhdcompanion.Components.TimeSelectDialogBox
import it.walmann.adhdcompanion.Components.getReminders
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.Reminders
import it.walmann.adhdcompanion.R

@Composable
fun RemindersScreen(modifier: Modifier, navController: NavController, context: Context) {
    Scaffold(
        topBar = { MyTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(
//                containerColor = Color.Black,
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xff8d6e63)),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            val temp = getReminders(context = context) //Reminders()

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
                    painter = painterResource(id = R.drawable.bing),
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


