package it.walmann.adhdcompanion.Components

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.File
import java.io.FileInputStream


//fun checkInternalFiles(context: Context): Unit {
//    val internalFilesList = arrayOf("db_reminders")
//    for (fileName in internalFilesList) {
////        try {
//        val file = File(context.filesDir, fileName)
//        val fileExists = file.exists()
//
//        val file2 = "Hello"
//
////        }
//    }
//}

fun getReminders(context: Context): List<String> {
// TODO Create system for saving, creating, and loading Reminders.
    val filename = "db_reminders"
    val file = File(context.filesDir, filename)

//    val testArray = arrayListOf("reminder", "reminder2", "reminder5")

    // Check if file Exists
    if (!file.exists()) { // If no exists, create the file.

        file.writeText(arrayListOf<String>().toString())
    }

    // Read the file, and convert to List
    val fileContent = file.readText().split(",").toList()

    return fileContent
}

