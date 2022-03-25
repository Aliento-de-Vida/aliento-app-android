package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.home.Notification
import com.alientodevida.alientoapp.domain.logger.Logger
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
import com.alientodevida.alientoapp.domain.home.NotificationRequest as DomainNotificationRequest
import com.alientodevida.alientoapp.domain.home.Attachment as DomainAttachment

@HiltViewModel
class EditCreateNotificationViewModel @Inject constructor(
  private val homeRepository: HomeRepository,
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
  
  private val _notification = MutableStateFlow<ViewModelResult<Notification>?>(null)
  val notification: StateFlow<ViewModelResult<Notification>?> = _notification
  
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
    
    stateFlowNullableResult(
      stateFlow = _notification,
      dispatcher = coroutineDispatchers.default
    ) {
      if (notification.isNew)
        homeRepository.createNotification(notification.toDomain(domainAttachment))
      else
        homeRepository.editNotification(notification.toDomain(domainAttachment))
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