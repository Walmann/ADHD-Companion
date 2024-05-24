package it.walmann.adhdcompanion.MyObjects

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Calendar


@Entity
data class reminder(
    /**
     * Reminder object.
     * uid is usually a Calendar.timeInMillis
     */
    @PrimaryKey var uid: Long,
//    @ColumnInfo(name = "reminderKey") val reminderKey: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "reminderCalendar") var reminderCalendar: Calendar,
    @ColumnInfo(name = "reminderImage") val reminderImage: String,
    @ColumnInfo(name = "reminderImageFullPath") val reminderImageFullPath: String,
    @ColumnInfo(name = "reminderNote") val reminderNote: String = "",
) {
    companion object {
        fun create(): reminder {
            val cal = Calendar.getInstance()
            return reminder(
                uid = cal.timeInMillis,
                reminderCalendar = cal,
                reminderImage = "",
                reminderImageFullPath = ""
            )
        }
    }
}


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
        val new =
            "${reminder.reminderKey},${reminder.reminderCalendar},${reminder.reminderImage},${reminder.reminderNote}"
        return new
    }


}