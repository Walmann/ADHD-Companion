package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.util.Log
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





//suspend fun debugTestSQLdb(context: Context) {
//    Log.i("DebugSQL", "Entered debugTestSQLdb")
//    Log.i("DebugSQL", "Creating database")
//
//
////    val db = databaseBuilder(
////        context,
////        ReminderDatabase::class.java, "reminder"
////    ).build()
//
//    Log.i("DebugSQL", "Creating reminder")
//    val testReminder = reminder(uid = Calendar.getInstance().timeInMillis)
//    Log.i("DebugSQL", "Created reminder: ${testReminder}")
//
//    Log.i("DebugSQL", "Trying to add testReminder to Database")
//    db.ReminderDao().insertAll(testReminder)
//
//    Log.i("DebugSQL", "Trying to retrieve database")
//    val temp2 = db.ReminderDao().getAll()
//
//    Log.i("DebugSQL", "Got this from database: ${temp2}")
//
//    Log.i("DebugSQL", "Trying to retrieve database")
//    val temp3 = db.ReminderDao().getReminder(1715669767967)
//
//    Log.i("DebugSQL", "Got this from database: ${temp3}")
//
//    Log.i("DebugSQL", "Done in debugTestSQLdb")
//    print("")
//
//
//}


//@Serializable
//class myClass(val name: String, val number: Int, val calendar: Calendar)
//
//fun ThingsToTest(context: Context) {
//
//
//    val data2 = myReminder()
//
//    val data = myClass(name = "myName! æøå", number = 1942, calendar = Calendar.getInstance())
//
//    val encodedgson = Gson().toJson(data)
//
////    val encoded = Json.encodeToString(data)
//
//    val temp1 = ""
//
//}

//object myClassSerializer : KSerializer<myClass> {
//    override val descriptor: SerialDescriptor
//        get() = TODO("Not yet implemented")
//
//    override fun serialize(encoder: Encoder, value: myClass) {
//
//        val string = value.toString()
//        encoder.encodeString(string)
//        val temp = ""
//    }
//
//    override fun deserialize(decoder: Decoder): myClass {
//        val string = decoder.decodeString()
//        return myClass(name = string, number = 0, calendar = Calendar.getInstance())
//    }
//
//
//}