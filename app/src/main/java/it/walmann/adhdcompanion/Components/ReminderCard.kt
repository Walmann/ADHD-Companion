package it.walmann.adhdcompanion.Components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.walmann.adhdcompanion.R
import java.io.File



/*TODO
*  Use this in both ReminderList and SingleReminder.
*  When in SingleReminder, create a version that can be edited. Add a Pencil to the right of the Clock and Date.
*  When in Reminder list, disable the editing. Do not show the pencil.
* */

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