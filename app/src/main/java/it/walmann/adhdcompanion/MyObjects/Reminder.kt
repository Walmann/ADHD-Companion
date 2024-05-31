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
    @ColumnInfo(name = "reminderCalendar") var reminderCalendar: Calendar,
    @ColumnInfo(name = "reminderImage") val reminderImage: String,
    @ColumnInfo(name = "reminderImageFullPath") val reminderImageFullPath: String,
    @ColumnInfo(name = "reminderNote") var reminderNote: String = "",
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
