package it.walmann.adhdcompanion.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.MyObjects.getReminder
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.util.Calendar

@Composable
fun ReminderDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    calendarId: String,
    context: Context = LocalContext.current,
//    calendar: String,
) {
//    val temp = calendarId.toLong()


    val reminder = if (LocalInspectionMode.current) {
        val reminderClass = myReminder()
        val reminderOrig = reminderClass.createMap(newReminder = Calendar.getInstance())
        // TODO NEXT WORK: Fortsett her. Få Image til å fungere i preview. Last en en Resource og bruk den bare.


    } else {
        getReminder(calendarId.toLong(), context)
    }

    Scaffold(
        topBar = { MyTopAppBar() },

        ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text(text = reminder.toString())
            Image(
                painter = rememberAsyncImagePainter(reminder["reminderImage"]),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = modifier
                    .weight(7f)
//                .fillMaxSize()
                    .padding(vertical = 10.dp)
//                .heightIn(50.dp, 100.dp)

            )


        }

    }
}


@Preview
@Composable
private fun ReminderDetailsPreview(
) {
    ReminderDetails(
        calendarId = "1714976415585",
        navController = rememberNavController()
    )

}