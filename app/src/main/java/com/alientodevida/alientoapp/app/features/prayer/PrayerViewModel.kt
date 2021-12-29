package com.alientodevida.alientoapp.app.features.prayer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
  coroutineDispatchers: CoroutineDispatchers,
  errorParser: ErrorParser,
  logger: Logger,
  preferences: Preferences,
  savedStateHandle: SavedStateHandle,
  application: Application,
) : BaseViewModel(
  coroutineDispatchers,
  errorParser,
  logger,
  preferences,
  savedStateHandle,
  application,
) {
  val home = preferences.home
  
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
  
  fun emailSent() {
    _sendEmail.value = null
  }
  
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
    _sendEmail.value = Pair(
      "Petición de oración: $selectedTopic", """
							Datos de contacto:
							
							nombre: ${name!!}
							email: ${email!!}
							whatsapp: ${whatsapp!!}
							
							mensaje:
							
							${message!!}							
						""".trimIndent()
    )
  }
  
}
