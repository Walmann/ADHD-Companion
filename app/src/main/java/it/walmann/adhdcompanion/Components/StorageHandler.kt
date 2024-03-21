package it.walmann.adhdcompanion.Components

import android.content.Context
import android.content.Context.MODE_PRIVATE

fun getReminders(context: Context){
// TODO NEXT Create system for saving, creating, and loading Reminders.
    val temp = context.filesDir.listFiles()
    val fos = context.openFileOutput("db_reminders", MODE_PRIVATE)
    fos.write("Hello!".toByteArray())
    val fis = context.openFileInput("db_reminders")
    val temp2 = fis.read()
    val temp21234 = context.filesDir
}