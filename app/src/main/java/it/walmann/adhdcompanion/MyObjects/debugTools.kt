package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import it.walmann.adhdcompanion.Handlers.Reminder.reminderSave
import java.io.File
import java.util.Calendar
import java.util.Random

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





@Composable
fun debugGetDebugReminders(amount: Int): MutableList<reminder> {
    val reminderList = remember { mutableListOf<reminder>() }
//    val reminderList: List<reminder.Companion>? = null
//    val temp = listOf(reminder)
    val calendarUnits = listOf(
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_YEAR,
        Calendar.HOUR_OF_DAY,
        Calendar.MINUTE
    )
    val tekstListe = listOf(
        "",
        "",
        "",
        "Sol",
        "Regn",
        "Kjærlighet",
        "Eventyr",
        "Frihet",
        "Vinterlandskap",
        "Sommermorgen",
        "Stjernehimmel",
        "Bølgeskvulping",
        "Skogens ro",
        "Smilende ansikt",
        "Sint ansikt",
        "❤️ Hjerte",
        "Dritt",
        "$ Dollar",
        "€ Euro",
        "# Hashtag",
        "@ Att",
        "\t Tabulator",
        "\n Linjeskift",
        "‘ Enkel anførselstegn",
        "“ Dobbel anførselstegn",
        "\\ Backslash"
    )
    repeat(amount) {
        val tempCal = Calendar.getInstance()
        tempCal.add(Random().nextInt(calendarUnits.size), Random().nextInt(listOf(1..10).size))
        val rem = reminder(
            uid = tempCal.timeInMillis,
            reminderCalendar = tempCal,
            reminderNote = Random().nextInt(tekstListe.size).toString(),
            reminderImageFullPath = "",
            reminderImage = ""
        )

        reminderList.add(rem)
    }

    return reminderList
}