package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Calendar

fun debugDeleteInternalStorage(context: Context) {
    val dir = context.filesDir
    val listOfFiles = dir.list()!!
    for (file in listOfFiles) {
        if (file != "profileInstalled") {
            val f = File(dir, file)
            f.delete()
        }
    }
}



fun DebugCreateManyReminders(context: Context, amount: Int){
    repeat(amount){
        val call = Calendar.getInstance()
        val newReminder = reminder(uid = call.timeInMillis, reminderCalendar = call, reminderImage = Uri.EMPTY.toString(), reminderImageFullPath = Uri.EMPTY.toString())
        reminderSave(context = context, reminderToSave = newReminder)
    }
}