package it.walmann.adhdcompanion.Handlers.Reminder

import android.content.Context
import it.walmann.adhdcompanion.MyObjects.myReminder
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream

//fun reminderLoad() {

//fun reminderLoad(context: Context): LinkedHashMap<String, java.util.LinkedHashMap<String, Any>> {
//    try {
//        var returningMap: LinkedHashMap<String, LinkedHashMap<String, Any>>
//        val fis: FileInputStream = context.openFileInput(loadSetting(context, "reminderDbLoc"))
//        val ois = ObjectInputStream(fis)
//        returningMap = ois.readObject() as LinkedHashMap<String, LinkedHashMap<String, Any>>
//
//        val temp1 = returningMap.toList().sortedBy { it.first }
//        val result = linkedMapOf(*temp1.toTypedArray())
//
//        return result
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    return LinkedHashMap()
//}

//}


class reminderLoad {
    companion object {
        fun all(context: Context): LinkedHashMap<String, myReminder> {
            try {
                val returningMap: LinkedHashMap<String, myReminder>
                val fis: FileInputStream =
                    context.openFileInput("reminder_db")
//                    context.openFileInput(loadSetting(context, "reminderDbLoc").toString())
                val ois = ObjectInputStream(fis)
                returningMap = ois.readObject() as LinkedHashMap<String, myReminder>

                val temp1 = returningMap.toList().sortedBy { it.first }
                val result = linkedMapOf(*temp1.toTypedArray())

                // TODO NEXT Convert to myReminder!

                return result
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return LinkedHashMap()
        }

        fun single(
            reminderId: Long,
            context: Context,
        ): myReminder {
            val reminderMap = reminderLoad.all(context = context)
            val reminder = reminderMap[reminderId.toString()] ?: myReminder()

            return reminder
        }
    }
}




