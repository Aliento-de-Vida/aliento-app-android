package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.app.Application
import android.os.Build
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
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.inject.Inject
import kotlin.io.path.absolutePathString
import com.alientodevida.alientoapp.domain.notification.Attachment as DomainAttachment
import com.alientodevida.alientoapp.domain.notification.NotificationRequest as DomainNotificationRequest

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
  
  val initialNotificationRequest: NotificationRequest =
    (savedStateHandle.get<Notification>("notification") ?: Notification.empty()).toNotificationRequest()
  
  private val _notificationRequest = MutableStateFlow<ViewModelResult<NotificationRequest>>(
    ViewModelResult.Success(initialNotificationRequest)
  )
  val notificationRequest: StateFlow<ViewModelResult<NotificationRequest>> = _notificationRequest
  
  private val currentNotificationRequest: NotificationRequest get() =
    (notificationRequest.value as? ViewModelResult.Success<NotificationRequest>)?.data ?: Notification.empty()
      .toNotificationRequest()
  
  private val _isNotificationRequestComplete = MutableStateFlow(false)
  val isNotificationRequestComplete: StateFlow<Boolean> = _isNotificationRequestComplete
  
  init {
    _isNotificationRequestComplete.value = currentNotificationRequest.isComplete
  }
  
  fun onNotificationTitleChanged(newTitle: String) {
    _notificationRequest.value = ViewModelResult.Success(currentNotificationRequest.copy(title = newTitle))
    _isNotificationRequestComplete.value = currentNotificationRequest.isComplete
  }

  fun onNotificationDescriptionChanged(newDescription: String) {
    _notificationRequest.value = ViewModelResult.Success(currentNotificationRequest.copy(content = newDescription))
    _isNotificationRequestComplete.value = currentNotificationRequest.isComplete
  }

  fun onNotificationImageNameChanged(newImageName: String) {
    _notificationRequest.value = ViewModelResult.Success(currentNotificationRequest.copy(image = com.alientodevida.alientoapp.domain.common.Image(newImageName)))
    _isNotificationRequestComplete.value = currentNotificationRequest.isComplete
  }

  fun onNotificationImageChanged(newAttachment: Attachment?) {
    _notificationRequest.value = ViewModelResult.Success(currentNotificationRequest.copy(
      attachment = newAttachment,
      image = com.alientodevida.alientoapp.domain.common.Image(newAttachment?.displayName ?: "")
    ))
    _isNotificationRequestComplete.value = currentNotificationRequest.isComplete
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
    
    stateFlowResult(stateFlow = _notificationRequest) {
      if (notification.isNew)
        notificationRepository.createNotification(notification.toDomain(domainAttachment)).toNotificationRequest()
      else
        notificationRepository.editNotification(notification.toDomain(domainAttachment)).toNotificationRequest()
    }
  }
  
}

private fun NotificationRequest.toDomain(domainAttachment: DomainAttachment?) = DomainNotificationRequest(
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