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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MyObjects.debugDeleteInternalStorage
import it.walmann.adhdcompanion.MyObjects.getReminderDate
import it.walmann.adhdcompanion.MyObjects.getReminderTime
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.R
import java.io.File

@Composable
fun RemindersScreen(modifier: Modifier, navController: NavController, context: Context) {
    val newReminder by remember { mutableStateOf(myReminder()) }
    Scaffold(
        topBar = { MyTopAppBar() },
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
                .background(Color(0xff8d6e63)),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { debugDeleteInternalStorage(context) }) {
                Text(text = "DELETE INTERLAN STORAGE!!!")
            }
            val reminderArray = newReminder.loadReminders(context)
            print("")
            reminderArray.forEach { element ->// (key, value) ->
                val currReminder = element.value
                ReminderCard( // TODO Create a "Reminder details" Screen.
//                    reminderTime = currReminder["reminderTime"].toString(),
                    reminderTime = getReminderTime(currReminder),
                    reminderDate = getReminderDate(currReminder),
                    reminderText = currReminder["reminderNote"].toString(),
                    reminderImage = currReminder["reminderImage"].toString(),
                    modifier = Modifier,
                    context = context
                )
            }

//            ReminderCard(
//                ReminderTime = "10:45",
//                ReminderDate = "03.02.2024",
//                ReminderText = "Lorem Ipsum is simply dummy ",
//                modifier = Modifier
//            )
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
//            var reminderImage: Bitmap? = null
            val imgFile = File(context.filesDir, reminderImage)
            val RemindImage = if (imgFile.exists()) {
                BitmapFactory.decodeFile(imgFile.absolutePath)
            } else {
                BitmapFactory.decodeResource(context.resources, R.drawable.bing)
            }

//            val temp1 = imgFile.absolutePath
//            val temp2 = imgFile.absoluteFile
//            val temp3 = imgFile.path
//            val reminderImage = BitmapFactory.decodeFile(imgFile.path)

            Row(
//                modifier = modifier
//                .background(Color.Black)
            ) {
                Column( // Time and Date Info
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .width(200.dp)
                        .height(200.dp)


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

                }
                Image(
                    bitmap = RemindImage.asImageBitmap(),
                    modifier = modifier
                        .height(200.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Fit,
//                    painter = painterResource(id = R.drawable.bing),
                    contentDescription = null
                )
            }
            Text(
                text = reminderText,
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


