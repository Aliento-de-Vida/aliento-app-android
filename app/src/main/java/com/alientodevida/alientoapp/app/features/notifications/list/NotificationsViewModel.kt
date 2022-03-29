package com.alientodevida.alientoapp.app.features.notifications.list

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.notification.Notification
import com.alientodevida.alientoapp.domain.notification.NotificationRepository
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class NotificationsUiState(
  val notifications: List<Notification>,
  val loading: Boolean,
  val messages: List<Message>,
  val isAdmin: Boolean,
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(
  private val notificationRepository: NotificationRepository,
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
  
  private val _viewModelState = MutableStateFlow(
    NotificationsUiState(
      notifications = emptyList(),
      loading = true,
      messages = emptyList(),
      preferences.isAdmin,
    )
  )
  val viewModelState: StateFlow<NotificationsUiState> = _viewModelState
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun getNotifications() {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        val notifications = notificationRepository.getNotifications().filter { it.image != null }
        _viewModelState.update { it.copy(
          notifications = notifications,
          messages = listOf(Message.Localized.Informational(
            id = UUID.randomUUID().mostSignificantBits,
            title = "Éxito!",
            message = "Notificación guardada con éxito!",
            action = "",
          ))
        ) }
      
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
  fun deleteNotification(notification: Notification) {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        notificationRepository.deleteNotification(notification.id)
        val notifications = viewModelState.value.notifications.filter { it.id != notification.id }
        _viewModelState.update { it.copy(notifications = notifications) }
      
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
}