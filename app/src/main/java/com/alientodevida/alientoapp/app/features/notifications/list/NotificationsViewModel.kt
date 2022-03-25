package com.alientodevida.alientoapp.app.features.notifications.list

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.notification.Notification
import com.alientodevida.alientoapp.domain.notification.NotificationRepository
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

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
  
  val isAdmin: Boolean get() = preferences.isAdmin
  
  private val _notifications = MutableStateFlow<ViewModelResult<List<Notification>>>(ViewModelResult.Loading)
  val notifications: StateFlow<ViewModelResult<List<Notification>>> = _notifications
  
  fun getNotifications() {
    stateFlowResult(_notifications) { notificationRepository.getNotifications().filter { it.image != null } }
  }
  
  fun deleteNotification(notification: Notification) {
    val notifications = (notifications.value as? ViewModelResult.Success)?.data ?: emptyList()
    stateFlowResult(_notifications) {
      notificationRepository.deleteNotification(notification.id)
      notifications.filter { it.id != notification.id }
    }
  }
  
}