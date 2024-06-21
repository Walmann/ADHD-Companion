package it.walmann.adhdcompanion.Components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import it.walmann.adhdcompanion.CupcakeScreen
import it.walmann.adhdcompanion.MainActivity
import it.walmann.adhdcompanion.MyObjects.deleteNotification
import it.walmann.adhdcompanion.MyObjects.reminder
import it.walmann.adhdcompanion.R
import it.walmann.adhdcompanion.conditional
import java.io.File
import java.util.Calendar


/*TODO
*  Use this in both ReminderList and SingleReminder.
*  When in SingleReminder, create a version that can be edited. Add a Pencil to the right of the Clock and Date.
*  When in Reminder list, disable the editing. Do not show the pencil.
* */


@Composable
fun ReminderCard(
    reminder: reminder,
    context: Context,
    modifier: Modifier = Modifier,
    isEditable: Boolean = false,
    isNoteScrollable: Boolean = false,
    onClick: () -> Unit = {},
    onEditTimeClick: () -> Unit = {},
    onEditDateClick: () -> Unit = {},
    onNoteDataChange: (String) -> Unit = {}
) {

    var reminderNoteValue by remember { mutableStateOf(reminder.reminderNote) }
    val imgFile = File(context.filesDir, reminder.reminderImage)
    val RemindImage = if (imgFile.exists()) {
        rememberAsyncImagePainter(imgFile)
    } else {
        rememberAsyncImagePainter(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.placeholder_reminderimage
            )
        )
    }
    Card(
        onClick = onClick,
        modifier = modifier
            .widthIn(max = 500.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
//            .heightIn(min = 250.dp)
            .padding(vertical = 5.dp)
    ) {
        if (isEditable) { // DELETE REMINDER
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
            ) {
                IconButton(
                    modifier = modifier.padding(0.dp),
                    onClick = {
                        MainActivity.reminderDB.ReminderDao().delete(reminder)
                        deleteNotification(context = context, reminder = reminder)
                        MainActivity.navigator.navigate(CupcakeScreen.ReminderList.name)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete reminder",
                        modifier = modifier.padding(0.dp)
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
//                    .fillMaxSize()
//                    .weight(9f)
                    .padding(10.dp)
            ) {
                //            Row(modifier = modifier.weight(5f), horizontalArrangement = Arrangement.Center) {
                Column( // Time and Date Info
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = when (isEditable) {
                            true -> Modifier.clickable { onEditTimeClick() }
                            false -> Modifier
                        }
                    ) {
                        Text(
                            text = calendarToTime(reminder.reminderCalendar),
                            fontSize = 50.sp,
                        )
                        if (isEditable) {
                            //                        IconButton(
                            //                            modifier = Modifier.height(IntrinsicSize.Max),
                            //                            onClick = onEditTimeClick
                            //                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                "Edit Time",
                                modifier.height(IntrinsicSize.Max)
                            )
                        }
                        //                    }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = when (isEditable) {
                            true -> Modifier.clickable { onEditDateClick() }
                            false -> Modifier
                        }
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
                                    "Edit Date",
                                    modifier.height(IntrinsicSize.Max)
                                )
                            }
                        }
                    }


                }

                Image(
                    painter = RemindImage,
                    modifier = modifier
                        //                    .weight(5f)
                        .padding(horizontal = 10.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )

            }
            Row( // Reminder Note
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
//                    .weight(1f)
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scrollOffset by remember { mutableStateOf(0) }
                TextField(
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                    maxLines = if (isNoteScrollable) Int.MAX_VALUE else 2,
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(
                            enabled = isNoteScrollable,
                            state = rememberScrollState(scrollOffset)
                        )
                        .conditional(isNoteScrollable, {
                            height(IntrinsicSize.Min)

                        }, { heightIn(min = 20.dp) }),
                    singleLine = false,
                    value = reminderNoteValue,
                    readOnly = (if (isEditable) false else true),
                    onValueChange = {
                        reminderNoteValue = it
                        onNoteDataChange(it)
                    },
                    placeholder = {
                        val placeholderText = if (isEditable) "Reminder note" else ""
                        Text(text = placeholderText)
                    }
                )
            }
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
    val fixedMonth = calendar.get(Calendar.MONTH) + 1

    val date = calendar.get(Calendar.DATE).toString().padStart(2, '0')
    val month = fixedMonth.toString().padStart(2, '0')
    val year = calendar.get(Calendar.YEAR).toString().padStart(2, '0')
    return "${date}.${month}.${year}"
}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFFF5722)
@PreviewScreenSizes
@Composable
private fun ReminderCardPreview() {
    val longText =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
    val shortText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    Column {

        val cal1 = reminder.create()
        cal1.reminderCalendar.add(Calendar.MINUTE, 5)

        ReminderCard(
            reminder = cal1,
            context = LocalContext.current,
            isEditable = true
        )
        val cal3 = reminder.create()
        cal3.reminderNote = longText + longText

        cal3.reminderCalendar.add(Calendar.MINUTE, -5)
        ReminderCard(
            reminder = cal3,
            context = LocalContext.current,
            isEditable = false,
            isNoteScrollable = true
        )

        val cal2 = reminder.create()
        cal2.reminderCalendar.add(Calendar.MINUTE, -5)
        ReminderCard(
            reminder = cal2,
            context = LocalContext.current,
            isEditable = true
        )
    }
}
