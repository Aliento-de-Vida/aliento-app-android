package com.alientodevida.alientoapp.app.ui.prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.AppController
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.entities.UserFriendlyError
import com.alientodevida.alientoapp.domain.entities.network.CsrfToken
import com.alientodevida.alientoapp.domain.entities.network.base.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _isDataValid = MutableLiveData<Boolean>()
    val isDataValid: LiveData<Boolean>
        get() = _isDataValid

    private val _messageToShow = MutableLiveData<Pair<String, String>>()
    val messageToShow: LiveData<Pair<String, String>>
        get() = _messageToShow

	private val _sendEmail = MutableLiveData<Pair<String, String>?>()
	val sendEmail: LiveData<Pair<String, String>?>
		get() = _sendEmail

	private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val _onError = MutableLiveData<UserFriendlyError?>()
    val onError: LiveData<UserFriendlyError?> = _onError

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

    fun errorHandled() { _onError.value = null }

	fun emailSent() { _sendEmail.value = null }

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

            when (val response = repository.sendPrayerRequest(
	            AppController.get<CsrfToken>(CsrfToken.key)?.csrfToken ?: "",
                selectedTopic!!,
                name!!,
                email!!,
                whatsapp!!,
                message!!
            )) {
                is ApiResult.Success -> {
                    if (response.body.status == "ok") {
                        _messageToShow.value = Pair(
                            "Felicidades!",
                            "${response.body.response}\nPronto nos pondremos en contacto con usted $name"
                        )
                    } else {
                    	sendEmail()
	                    /* _messageToShow.value =
		                    Pair("Lo sentimos", "Ha habido un error, por favor intente más tarde") */
                    }
                }
                is ApiResult.Failure -> {
	                sendEmail()
                    // _onError.value =
                    //    UserFriendlyError(response.responseError)
                }
            }

            _isGettingData.value = false
        }
    }

	fun sendEmail() {
		_sendEmail.value = Pair("Petición de oración: $selectedTopic", """
							Datos de contacto:
							
							nombre: ${name!!}
							email: ${email!!}
							whatsapp: ${whatsapp!!}
							
							mensaje:
							
							${message!!}							
						""".trimIndent())
	}
}
