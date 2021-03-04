package com.alientodevida.alientoapp.ui.prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.entities.local.BankAccount
import com.alientodevida.alientoapp.data.entities.local.DonationType
import com.alientodevida.alientoapp.data.entities.local.PaymentItem
import com.alientodevida.alientoapp.data.entities.local.Paypal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PrayerViewModel : ViewModel() {
    private val _isDataValid = MutableLiveData<Boolean>()
    val isDataValid: LiveData<Boolean>
        get() = _isDataValid

    private val _messageToShow = MutableLiveData<Pair<String, String>>()
    val messageToShow: LiveData<Pair<String, String>>
        get() = _messageToShow

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    var name: String? = null
    var email: String? = null
    var whatsapp: String? = null

    var message: String? = null
    val topics: List<String> = arrayListOf(
            "Elige un motivo de oración",
            "Salud",
            "Finanzas",
            "Familiar",
            "Personal",
    )

    var selectedTopic: String? = null

    fun validation() {
        _isDataValid.value = (
                selectedTopic.isNullOrBlank().not() &&
                name.isNullOrBlank().not() &&
                email.isNullOrBlank().not() &&
                whatsapp.isNullOrBlank().not() &&
                message.isNullOrBlank().not()
        )
    }
    fun sendPrayerRequest() {
        _isGettingData.value = true
        viewModelScope.launch {
            delay(2000)
            _messageToShow.value = Pair("Felicidades!", "Se ha enviado su petición de oración!\nPronto nos pondremos en contacto con usted $name")
            _isGettingData.value = false
        }
    }

}