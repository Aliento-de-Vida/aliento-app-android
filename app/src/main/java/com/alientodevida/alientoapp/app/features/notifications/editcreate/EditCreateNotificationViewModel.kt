package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.app.Application
import android.os.Build
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
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.inject.Inject
import kotlin.io.path.absolutePathString
import com.alientodevida.alientoapp.domain.notification.Attachment as DomainAttachment
import com.alientodevida.alientoapp.domain.notification.NotificationRequest as DomainNotificationRequest

data class NotificationUiState(
  val notificationRequest: NotificationRequest,
  val loading: Boolean,
  val messages: List<Message>,
)

private fun NotificationRequest.toDomain(domainAttachment: DomainAttachment?) =
  DomainNotificationRequest(
    id = id,
    title = title,
    content = content,
    image = image,
    date = date,
    attachment = domainAttachment,
  )

fun Notification.toNotificationRequest() =
  NotificationRequest(
    id = id,
    title = title,
    content = content,
    image = image,
    date = date,
    attachment = null,
  )

@HiltViewModel
class EditCreateNotificationViewModel @Inject constructor(
  private val notificationRepository: NotificationRepository,
  coroutineDispatchers: CoroutineDispatchers,
  errorParser: ErrorParser,
  logger: Logger,
  preferences: Preferences,
  savedStateHandle: SavedStateHandle,
  val application: Application,
) : BaseViewModel(
  coroutineDispatchers,
  errorParser,
  logger,
  preferences,
  savedStateHandle,
  application,
) {
  
  private val initialNotificationRequest: NotificationRequest =
    (savedStateHandle.get<Notification>("notification")
      ?: Notification.empty()).toNotificationRequest()
  
  private val _viewModelState = MutableStateFlow(
    NotificationUiState(
      notificationRequest = initialNotificationRequest,
      loading = false,
      messages = emptyList(),
    )
  )
  val viewModelState: StateFlow<NotificationUiState> = _viewModelState
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.value = viewModelState.value.copy(messages = newMessages)
  }
  
  fun onNotificationTitleChanged(newTitle: String) {
    _viewModelState.value = viewModelState.value.copy(
      notificationRequest = viewModelState.value.notificationRequest.copy(title = newTitle)
    )
  }
  
  fun onNotificationDescriptionChanged(newDescription: String) {
    _viewModelState.value = viewModelState.value.copy(
      notificationRequest = viewModelState.value.notificationRequest.copy(content = newDescription)
    )
  }
  
  fun onNotificationImageNameChanged(newImageName: String) {
    _viewModelState.value = viewModelState.value.copy(
      notificationRequest = viewModelState.value.notificationRequest.copy(
        image = com.alientodevida.alientoapp.domain.common.Image(newImageName)
      )
    )
  }
  
  fun onNotificationImageChanged(newAttachment: Attachment?) {
    _viewModelState.value = viewModelState.value.copy(
      notificationRequest = viewModelState.value.notificationRequest.copy(
        image = com.alientodevida.alientoapp.domain.common.Image(newAttachment?.displayName ?: "")
      )
    )
  }
  
  fun saveNotification(notification: NotificationRequest) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    
    val domainAttachment = notification.attachment?.let { attachment ->
      val attachmentsDir = Paths.get(application.filesDir.path, "notification-attachment").toFile()
      attachmentsDir.mkdir()
      
      application.parcelFileDescriptor(attachment.uri)?.use { descriptor ->
        FileInputStream(descriptor.fileDescriptor).use { stream ->
          val outputPath = Paths.get(attachmentsDir.path, attachment.displayName)
          Files.copy(stream, outputPath, StandardCopyOption.REPLACE_EXISTING)
          
          DomainAttachment(attachment.displayName, outputPath.absolutePathString())
        }
      }
    }
    
    viewModelScope.launch {
      try {
        _viewModelState.value = viewModelState.value.copy(loading = true)
        val value = if (notification.isNew)
          notificationRepository.createNotification(notification.toDomain(domainAttachment))
        else
          notificationRepository.editNotification(notification.toDomain(domainAttachment))
        
        val successMessage = Message.Localized.Informational(
          id = UUID.randomUUID().mostSignificantBits,
          title = "Éxito!",
          message = "Notificación guardada con éxito!",
          action = "",
        )
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(successMessage)
        _viewModelState.value = viewModelState.value.copy(
          notificationRequest = value.toNotificationRequest(),
          messages = messages
        )
        
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("EditCreateNotificationViewModel.saveNotification", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.value = viewModelState.value.copy(messages = messages)
      }
      _viewModelState.value = viewModelState.value.copy(loading = false)
    }
  }
  
}