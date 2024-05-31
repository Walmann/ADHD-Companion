package it.walmann.adhdcompanion.Handlers.Reminder

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import it.walmann.adhdcompanion.MyObjects.reminder
import java.util.Calendar


@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAll(): List<reminder>

    @Query("SELECT * FROM reminder WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<reminder>

    @Query("SELECT * FROM reminder WHERE uid IN (:uid)")
    fun getReminder(uid: Long): reminder

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
//    @TypeConverter
//    fun toString(reminder: reminder): String {
//        val newCal = ""
//        return newCal
//    }

//    @TypeConverter
//    fun stringToReminder(value: String): reminder { // Check here if there is something wrong with Reminders
//        Log.d("StringToReminder", "stringToReminder: Current value: ${value}")
//
//        val newReminder = reminder(uid = 1234123123, reminderCalendar = Calendar.getInstance(), reminderImage = "Uri.EMPTY", reminderImageFullPath = "Uri.EMPTY")
//        return newReminder
//    }

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