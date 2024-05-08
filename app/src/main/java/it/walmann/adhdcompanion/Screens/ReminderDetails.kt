package it.walmann.adhdcompanion.Screens
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.ScrollState
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.overscroll
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalInspectionMode
//import androidx.compose.ui.res.imageResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.content.res.ResourcesCompat
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter
//import coil.compose.rememberImagePainter
//import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
//import it.walmann.adhdcompanion.MyObjects.getReminder
//import it.walmann.adhdcompanion.MyObjects.myReminder
//import it.walmann.adhdcompanion.R
//import java.util.Calendar
//
//@Composable
//fun ReminderDetails(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    calendarId: String,
//    context: Context = LocalContext.current,
////    calendar: String,
//) {
////    val temp = calendarId.toLong()
//
//
//    val reminder = if (LocalInspectionMode.current) {
//        val reminderClass = myReminder()
//        val reminderOrig = reminderClass.createMap(newReminder = Calendar.getInstance())
//        reminderClass.createMap(newReminder = Calendar.getInstance())
//
//        // TODO NEXT WORK: Fortsett her. Få Image til å fungere i preview. Last en en Resource og bruk den bare.
////        print("")
//
//    } else {
//        getReminder(calendarId.toLong(), context)
//    }
//
//    val imageToShow = if (LocalInspectionMode.current) {
//        rememberAsyncImagePainter(R.drawable.placeholder_reminderimage)
//    } else {
//        rememberAsyncImagePainter(reminder?.get("reminderImageFullPath"))
//    }
//
//    Scaffold(
//        topBar = { MyTopAppBar() },
//
//        ) { innerPadding ->
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//
//        ) {
//            if (reminder != null) {
//                AsyncImage(
//                    model = reminder["reminderImageFullPath"].toString(),
//                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder_reminderimage),
//                    contentDescription = "",
//                    modifier = modifier
//                        .fillMaxHeight()
//                        .padding(vertical = 10.dp)
//
//                )
//            }
//            Column(
//                modifier = modifier
//                    .fillMaxSize()
//            ) {
//
//
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//                Text(text = reminder.toString())
//
//            }
//
//
//        }
//
//    }
//}
//
//@Composable
//fun debugPlaceholder(@DrawableRes debugPreview: Int) =
//    if (LocalInspectionMode.current) {
//        painterResource(id = debugPreview)
//    } else {
//        null
//    }
//
//@Preview
//@Composable
//private fun ReminderDetailsPreview(
//) {
//    ReminderDetails( // TODO next
//        calendarId = "1714976415585",
//        navController = rememberNavController()
//    )
//
//}