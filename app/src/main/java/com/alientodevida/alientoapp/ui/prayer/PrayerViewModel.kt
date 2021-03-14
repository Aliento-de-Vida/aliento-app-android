package com.alientodevida.alientoapp.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.network.CsrfToken
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class PrayerViewModel @ViewModelInject constructor(
    private val repository: Repository,
) : ViewModel() {

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

            try {
                val response = repository.sendPrayerRequest(
                    AppController.get<CsrfToken>(CsrfToken.key)!!.csrfToken,
                    selectedTopic!!,
                    name!!,
                    email!!,
                    whatsapp!!,
                    message!!
                )

                if (response.status == "ok") {
                    _messageToShow.value = Pair("Felicidades!", "${response.response}\nPronto nos pondremos en contacto con usted $name")
                } else {
                    _messageToShow.value = Pair("Lo sentimos", "Ha habido un error, por favor intente más tarde")
                }

            } catch (ex: HttpException) {
                ex.printStackTrace()
                _messageToShow.value = Pair("Lo sentimos", "Ha habido un error, por favor intente más tarde")
            }

            _isGettingData.value = false
        }
    }

}