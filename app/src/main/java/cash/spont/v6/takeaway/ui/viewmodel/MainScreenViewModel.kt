package cash.spont.v6.takeaway.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    var alertDialog = mutableStateOf(false)
    fun showDialog() {
        alertDialog.value = true
    }

    fun stopDialog() {
        alertDialog.value = false
    }

}