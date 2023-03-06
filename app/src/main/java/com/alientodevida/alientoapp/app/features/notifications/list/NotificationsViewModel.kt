package com.alientodevida.alientoapp.app.features.notifications.list

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.app.extensions.logScreenView
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
import javax.inject.Inject

data class NotificationsUiState(
  val notifications: List<Notification> = emptyList(),
  val loading: Boolean = true,
  val messages: List<Message> = emptyList(),
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
      analytics.logScreenView("notifications_screen")
  }

  val isAdmin = preferences.isAdminFlow
  
  private val _viewModelState = MutableStateFlow(NotificationsUiState())
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
        _viewModelState.update { it.copy(notifications = notifications,) }
      
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("NotificationsViewModel.getNotifications", tr = ex)
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
        logger.d("NotificationsViewModel.deleteNotification", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
}