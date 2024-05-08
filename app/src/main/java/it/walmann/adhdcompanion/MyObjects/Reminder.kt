package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import it.walmann.adhdcompanion.Handlers.Reminder.reminderLoad
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Calendar



class myReminder(
    var reminderCalendar: Calendar = Calendar.getInstance(),
    var reminderKey: String = reminderCalendar.timeInMillis.toString(),
    val reminderImage: Uri = Uri.EMPTY,
    val reminderNote: String = "You have a new reminder!",
    val reminderCreationCalendar: Calendar = Calendar.getInstance()
)