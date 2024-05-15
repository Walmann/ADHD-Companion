package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import it.walmann.adhdcompanion.Handlers.Reminder.reminderLoad
import kotlinx.serialization.Serializable
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Calendar


@Entity
data class reminder(
    @PrimaryKey val uid: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "reminderKey") val reminderKey: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "reminderCalendar") var reminderCalendar: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "reminderImage") val reminderImage: String = "",
    @ColumnInfo(name = "reminderImageFullPath") val reminderImageFullPath: String = "",
    @ColumnInfo(name = "reminderNote") val reminderNote: String = "",

    )



@TypeConverters(CalendarConverter::class)
class myReminder(
    var reminderCalendar: Calendar = Calendar.getInstance(),
    var reminderKey: String = reminderCalendar.timeInMillis.toString(),
    val reminderImage: Uri = Uri.EMPTY,
    val reminderNote: String = "You have a new reminder!",
//    val reminderCreationCalendar: Calendar = Calendar.getInstance()


)

class CalendarConverter {
    @TypeConverter
    fun fromReminderCalendarToLong(calendar: Calendar): Long {
        val newCal = Calendar.getInstance()
        return newCal.timeInMillis
    }

    @TypeConverter
    fun fromLongToReminderCalendar(long: Long): Calendar {
        val newCalendar = Calendar.getInstance()
        newCalendar.setTimeInMillis(long)
        return newCalendar
    }

    @TypeConverter
    fun toString(reminder: myReminder): String {
        val new = "${reminder.reminderKey},${reminder.reminderCalendar},${reminder.reminderImage},${reminder.reminderNote}"
        return new
    }


}