package it.walmann.adhdcompanion.myObjects

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel : ViewModel() {
    val selectedTime = mutableStateOf("")
}

