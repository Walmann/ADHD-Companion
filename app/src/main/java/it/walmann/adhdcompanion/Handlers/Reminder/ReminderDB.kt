package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import it.walmann.adhdcompanion.MyObjects.myReminder
import it.walmann.adhdcompanion.MyObjects.reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.util.Calendar


@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAll(): List<reminder>

    @Query("SELECT * FROM reminder WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<reminder>

    @Query("SELECT * FROM reminder WHERE uid IN (:uid)")
    fun getReminder(uid: Long): reminder

    @Insert
    fun insertAll(vararg reminders: reminder)

    @Delete
    fun delete(reminder: reminder)
}


@Database(entities = [reminder::class], version = 1, exportSchema = false)
@TypeConverters(ReminderConverter::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun ReminderDao(): ReminderDao
}

class ReminderConverter {
    @TypeConverter
    fun toString(reminder: reminder): String {
        val newCal = ""
        return newCal
    }

    @TypeConverter
    fun stringToReminder(value: String): reminder {
        val newReminder = reminder(uid = 1234123123)
        return newReminder
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long {
        val x = calendar.timeInMillis
        return x
    }

    @TypeConverter
    fun stringToCalendar(long: Long): Calendar {
        val newCal = Calendar.getInstance()
        newCal.setTimeInMillis(long)
        return newCal
    }


}