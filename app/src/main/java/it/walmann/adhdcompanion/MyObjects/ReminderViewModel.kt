package it.walmann.adhdcompanion.MyObjects

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel : ViewModel() {
    val selectedTime = mutableStateOf("")
}

