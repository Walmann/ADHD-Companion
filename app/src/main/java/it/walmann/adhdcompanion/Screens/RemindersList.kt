package it.walmann.adhdcompanion.Screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.CupcakeScreen
//import it.walmann.adhdcompanion.MyObjects.ReminderNotification
//import it.walmann.adhdcompanion.MyObjects.createScheduledNotification
import it.walmann.adhdcompanion.MyObjects.debugDeleteInternalStorage
import it.walmann.adhdcompanion.MyObjects.getReminderDate
import it.walmann.adhdcompanion.MyObjects.getReminderTime
import it.walmann.adhdcompanion.MyObjects.myReminder
//import it.walmann.adhdcompanion.MyObjects.newNotification
import it.walmann.adhdcompanion.R
import java.io.File
import java.util.Calendar
import kotlin.random.Random

@Composable
fun RemindersScreen(modifier: Modifier, navController: NavController, context: Context) {
    val newReminder by remember { mutableStateOf(myReminder()) }

    // TODO FUTURE I want to create a better looking app. Therefore the list needs a redesign.
    // Look at these links for inspiration:
    // https://dribbble.com/shots/5221219-Card-Vault-Mobile-App
    // https://dribbble.com/shots/12078609-Circle-Hook-Fisher-man-app-Micro-interactions

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
                .background(MaterialTheme.colorScheme.background)
            ,

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            val notificationThingy = newNotification(context, title = "Hello!", content = "Hello from content!")
            ElevatedButton(onClick = { debugDeleteInternalStorage(context) }) {
                Text(text = "DELETE INTERLAN STORAGE!!!")
            }
//            ElevatedButton(onClick = { // Alarm is not being triggered?
//                val tempCal = Calendar.getInstance().apply {
//                    timeInMillis = System.currentTimeMillis()
//                    add(Calendar.SECOND, 5)
//                }
////                tempCal.add(Calendar.SECOND, 5)
////                ReminderNotification(context = context, builder = notificationThingy, notificationID = Random.nextInt())
//                createScheduledNotification(context, reminderTime = tempCal)
//            }) {
//                Text(text = "Create alarm notification")
//            }
//            ElevatedButton(onClick = {
//                ReminderNotification(context = context, builder = notificationThingy, notificationID = Random.nextInt())
//            }) {
//                Text(text = "Show notification")
//            }


            val reminderArray = newReminder.loadReminders(context)
            print("")
            reminderArray.forEach { element ->// (key, value) ->
                val currReminder = element.value

                val currCalendar = Calendar.getInstance()
//                val temp2 = currReminder["reminderCalendar"]
                val temp3 = currReminder["reminderCalendar"].toString().toLong()
                currCalendar.setTimeInMillis(temp3)

                val temp = currCalendar.time
                ReminderCard( // TODO Create a "Reminder details" Screen.
//                    reminderTime = currReminder["reminderTime"].toString(),
//                    reminderTime = getReminderTime(currReminder),
//                    reminderDate = getReminderDate(currReminder),
                    reminderTime = "${currCalendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2,'0')}:${currCalendar.get(Calendar.MINUTE).toString().padStart(2,'0')}",
                    reminderDate = "${currCalendar.get(Calendar.DATE).toString().padStart(2,'0')}.${currCalendar.get(Calendar.MONTH).toString().padStart(2,'0')}.${currCalendar.get(Calendar.YEAR).toString().padStart(2,'0')}",
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
            Row{
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
                        .rotate(90f)
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


