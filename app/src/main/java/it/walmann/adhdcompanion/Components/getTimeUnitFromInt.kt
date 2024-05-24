package it.walmann.adhdcompanion.Components

import android.util.Log



fun getTimeUnitFromInt(value: Any): String {
    var currValue = value
    return when (currValue) {
        is Int -> {
            when (currValue) {
                0 -> "Seconds"
                12 -> "Minutes"
                11 -> "Hours"
                6 -> "Years"
                else -> "Time unit not implemented: $value"
            }
        }
        is String -> {
            when (currValue) {
                "" -> "Empty Time Unit"
                "0" -> "Seconds"
                "12" -> "Minutes"
                "11" -> "Hours"
                "6" -> "Years"
                else -> "Time unit not implemented: $value"
            }
        }
        else -> "Invalid input type: ${value::class.simpleName}"
    }
}