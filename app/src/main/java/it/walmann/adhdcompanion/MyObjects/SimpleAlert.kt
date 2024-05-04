package it.walmann.adhdcompanion.MyObjects

import android.app.AlertDialog
import android.content.Context
import java.util.Date

fun simpleAlert(title: String = "", message: String = "", context: Context) {
        // Create and show an alert dialog with notification details
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(
            message        )
        .setPositiveButton("Okay") { _, _ -> }
        .show()
}