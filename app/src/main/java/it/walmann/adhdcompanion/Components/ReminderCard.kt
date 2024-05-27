package it.walmann.adhdcompanion.Components

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import it.walmann.adhdcompanion.Handlers.Settings.AppSettings
import it.walmann.adhdcompanion.Handlers.Settings.getAppSetting
import it.walmann.adhdcompanion.Handlers.Settings.setAppSetting
import it.walmann.adhdcompanion.MyObjects.reminder
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar


/*TODO
*  Use this in both ReminderList and SingleReminder.
*  When in SingleReminder, create a version that can be edited. Add a Pencil to the right of the Clock and Date.
*  When in Reminder list, disable the editing. Do not show the pencil.
* */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderCard(
    reminder: reminder,
//    reminderTimeAndDate: Calendar,
//    reminderTime: String,
//    reminderDate: String,
//    reminderText: String,
//    reminderImage: String,
    context: Context,
    modifier: Modifier = Modifier,
    isEditable: Boolean = false,
    onClick: () -> Unit = {},
    onEditTimeClick: () -> Unit = {},
    onEditDateClick: () -> Unit = {},
) {

    val imgFile = File(context.filesDir, reminder.reminderImage)

//    val RemindImage = if (imgFile.exists()) {
//        BitmapFactory.decodeFile(imgFile.absolutePath)
//    } else {
//        BitmapFactory.decodeResource(context.resources, R.drawable.placeholder_reminderimage)
//    }
    val RemindImage = if (imgFile.exists()) {
        rememberAsyncImagePainter(imgFile)
    } else {
        rememberAsyncImagePainter(BitmapFactory.decodeResource(context.resources, R.drawable.placeholder_reminderimage))
    }


    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
//            .height(IntrinsicSize.Min)

//            .width(IntrinsicSize.Min)
//            .height(275.dp)
            .padding(vertical = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(1f, fill = false)
//                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
//            var reminderImage: Bitmap? = null

            Row(modifier = modifier.weight(5f), horizontalArrangement = Arrangement.Center) {
                Column( // Time and Date Info
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
//                        .weight(7f)
//                        .width(200.dp)
//                        .height(200.dp)


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
//                        modifier = modifier.auto,
                            text = calendarToTime(reminder.reminderCalendar),
                            fontSize = 50.sp,
                        )
                        if (isEditable) {
                            IconButton(
                                modifier = Modifier.height(IntrinsicSize.Max),
                                onClick = onEditTimeClick
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    "Edit Time",
                                    modifier.height(IntrinsicSize.Max)
                                )
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = calendarToDate(reminder.reminderCalendar),
                            fontSize = 20.sp,
                        )
                        if (isEditable) {
                            IconButton(
                                modifier = Modifier.height(IntrinsicSize.Max),
                                onClick = onEditDateClick
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    "Edit Time",
                                    modifier.height(IntrinsicSize.Max)
                                )
                            }
                        }
                    }

                    if (reminder.reminderNote != "") {
                        Text(
                            text = reminder.reminderNote,
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
                RemindImage,
                modifier = modifier
                    .weight(5f)
//                    .rotate(if (LocalInspectionMode.current) 0f else 90f)
                    .padding(10.dp),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )


        }
    }
}
/*TODO Add an Extended section. Same width, looks like a "Card" under this one. Make color a little darker.
        *  Contains the rest of the information in the Reminder. When it was created, GPS cords on where(?)*/

private fun calendarToTime(calendar: Calendar): String {
    return "${calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')}:${
        calendar.get(
            Calendar.MINUTE
        ).toString().padStart(2, '0')
    }"
}

private fun calendarToDate(calendar: Calendar): String {
    return "${
        calendar.get(Calendar.DATE).toString().padStart(2, '0')
    }.${calendar.get(Calendar.MONTH).toString().padStart(2, '0')}.${
        calendar.get(Calendar.YEAR).toString().padStart(2, '0')
    }"
}


@Preview
@Composable
private fun ReminderCardPreview() {
//    ADHDCompanionTheme {
//        Scaffold {
//            Surface(modifier = Modifier.padding(it)) {
    ReminderCard(
        reminder = reminder.create(),
        context = LocalContext.current,
        isEditable = true
    )
}

//        }
//    }
//}