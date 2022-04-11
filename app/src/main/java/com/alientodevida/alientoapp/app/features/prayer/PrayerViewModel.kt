package com.alientodevida.alientoapp.app.features.prayer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.features.notifications.list.NotificationsUiState
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.notification.Notification
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class PrayerUiState(
  val home: Home?,
  val name: String? = null,
  val email: String? = null,
  val whatsapp: String? = null,
  val message: String? = null,
  val topic: String? = null,
  val loading: Boolean = true,
  val messages: List<Message> = emptyList(),
) {
  val isValidForm = topic.isNullOrBlank().not() &&
      name.isNullOrBlank().not() &&
      email.isNullOrBlank().not() &&
      whatsapp.isNullOrBlank().not() &&
      message.isNullOrBlank().not()
}

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
  
  private val _viewModelState = MutableStateFlow(PrayerUiState(preferences.home))
  val viewModelState: StateFlow<PrayerUiState> = _viewModelState
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun onNameChanged(value: String) {
    _viewModelState.update { it.copy(name = value) }
  }
  
  fun onEmailChanged(value: String) {
    _viewModelState.update { it.copy(email = value) }
  }
  
  fun onWhatsappChanged(value: String) {
    _viewModelState.update { it.copy(whatsapp = value) }
  }
  
  fun sendEmail() {
  
  }
  
  val topics: List<String> = arrayListOf(
    "Elige un motivo de oración",
    "Salud",
    "Finanzas",
    "Familiar",
    "Personal",
  )
  
  /*fun sendPrayerRequest() {
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
  }*/
  
}
