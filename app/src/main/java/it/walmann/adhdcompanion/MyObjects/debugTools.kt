package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
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