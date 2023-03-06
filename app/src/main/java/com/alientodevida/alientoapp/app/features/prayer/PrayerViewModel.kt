package com.alientodevida.alientoapp.app.features.prayer

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsScreen
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.logger.Logger
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
  val topic: Int = 0,
  val topics: List<String> = arrayListOf(
    "Elige un motivo de oración",
    "Salud",
    "Finanzas",
    "Familiar",
    "Personal",
  ),
  val loading: Boolean = false,
  val messages: List<Message> = emptyList(),
) {
  val isValidForm = topic != 0 &&
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
  analytics: Analytics,
) : BaseViewModel(
  coroutineDispatchers,
  errorParser,
  logger,
  preferences,
  savedStateHandle,
  application,
) {

  init {
    analytics.logScreen(PrayerScreen())
  }

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
  
  fun onMessageChanged(value: String) {
    _viewModelState.update { it.copy(message = value) }
  }
  
  fun onTopicChanged(value: Int) {
    _viewModelState.update { it.copy(topic = value) }
  }
  
}

data class PrayerScreen(
  override val name: String = "PrayerScreen"
): AnalyticsScreen()