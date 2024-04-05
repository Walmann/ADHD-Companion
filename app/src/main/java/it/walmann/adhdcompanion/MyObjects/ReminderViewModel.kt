package it.walmann.adhdcompanion.MyObjects

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderViewModel  : ViewModel() {
        val selectedTime = mutableStateOf("")
    }

//data class SelectedTime(val time: Int){
//    var selectedTime: SelectedTime by remember { mutableStateOf(SelectedTime) }
//}