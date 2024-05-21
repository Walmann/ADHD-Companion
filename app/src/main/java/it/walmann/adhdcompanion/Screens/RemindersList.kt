package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.R
import java.io.File
import java.util.Calendar

@Composable
fun RemindersScreen(modifier: Modifier, navController: NavController, context: Context) {
//    val newReminder by remember { mutableStateOf(myReminder()) }

    // TODO FUTURE I want to create a better looking app. Therefore the list needs a redesign.
    // Look at these links for inspiration:
    // https://dribbble.com/shots/5221219-Card-Vault-Mobile-App
    // https://dribbble.com/shots/12078609-Circle-Hook-Fisher-man-app-Micro-interactions

    Scaffold(
//        topBar = { MyTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(
//                containerColor = Color.Black,
                onClick = {
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
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {



            val reminderArr = MainActivity.reminderDB.ReminderDao().getAll()



            if (reminderArr.isEmpty()) {
                CreateReminderInstructions(modifier = modifier)
            } else {
                // TODO Create title for this window.

                Text(text = "Reminders", style = MaterialTheme.typography.displayLarge)

                reminderArr.forEach { element ->// (key, value) ->
                    val currReminder = element
                    val reminderTime = "${
                        currReminder.reminderCalendar.get(Calendar.HOUR_OF_DAY).toString()
                            .padStart(2, '0')
                    }:${
                        currReminder.reminderCalendar.get(Calendar.MINUTE).toString()
                            .padStart(2, '0')
                    }"
                    val reminderDate = "${
                        currReminder.reminderCalendar.get(Calendar.DATE).toString().padStart(2, '0')
                    }.${
                        currReminder.reminderCalendar.get(Calendar.MONTH).toString()
                            .padStart(2, '0')
                    }.${
                        currReminder.reminderCalendar.get(Calendar.YEAR).toString().padStart(2, '0')
                    }"
                    ReminderCard(
                        reminderTime = reminderTime,
                        reminderDate = reminderDate,
                        reminderText = currReminder.reminderNote,
                        reminderImage = currReminder.reminderImage,
                        modifier = Modifier,
                        context = context,
                        onClick = { navController.navigate("${CupcakeScreen.ReminderDetails.name}/${currReminder.uid}") }
                    )
                }
            }
        }
    }
}


@Composable
fun ReminderCard(
    reminderTime: String,
    reminderDate: String,
    reminderText: String,
    reminderImage: String,
    context: Context,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.9f)
//            .height(275.dp)
            .padding(vertical = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
//            var reminderImage: Bitmap? = null
            val imgFile = File(context.filesDir, reminderImage)

            val RemindImage = if (imgFile.exists()) {
                BitmapFactory.decodeFile(imgFile.absolutePath)
            } else {
                BitmapFactory.decodeResource(context.resources, R.drawable.bing)
            }
            Row(modifier = modifier.weight(7f), horizontalArrangement = Arrangement.Center) {
                Column( // Time and Date Info
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
//                        .weight(7f)
//                        .width(200.dp)
//                        .height(200.dp)


                ) {
                    Text(
//                        modifier = modifier.auto,
                        text = reminderTime,
                        fontSize = 50.sp,

                        )
                    Text(
                        text = reminderDate,
                        fontSize = 20.sp,
                    )
                    if (reminderText != "") {
                        Text(
                            text = reminderText,
                            fontSize = 20.sp,
                            modifier = modifier
                                .padding(
                                    horizontal = 10.dp
                                )
                        )
                    }
                }
            }
            Image(
                bitmap = RemindImage.asImageBitmap(),
                modifier = modifier
                    .weight(3f)
                    .rotate(90f)
//                        .height(200.dp)
                    .padding(15.dp),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CreateReminderInstructions(modifier: Modifier = Modifier) {
    Column {
        Text(text = "To start creating new reminders, press '+' in the lower right corner")
    }
}


@Preview
@Composable
private fun RemindersScreenPreview() {
    RemindersScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        context = LocalContext.current
    )
}