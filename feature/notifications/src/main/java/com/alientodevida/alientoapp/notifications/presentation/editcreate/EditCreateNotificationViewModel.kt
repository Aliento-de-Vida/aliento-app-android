package com.alientodevida.alientoapp.notifications.presentation.editcreate

import android.app.Application
import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.designsystem.components.attachment.getDomainAttachment
import com.alientodevida.alientoapp.domain.common.Notification
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.extensions.removeExtension
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.notifications.domain.NotificationRepository
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.state.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import com.alientodevida.alientoapp.domain.common.Attachment as DomainAttachment
import com.alientodevida.alientoapp.notifications.domain.NotificationRequest as DomainNotificationRequest

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
        imageName = imageName,
        date = date,
        attachment = domainAttachment,
    )

fun Notification.toNotificationRequest() =
    NotificationRequest(
        id = id,
        title = title,
        content = content,
        imageName = image?.name ?: "",
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

    private val _viewModelState = MutableStateFlow(
        NotificationUiState(
            notificationRequest = Notification.empty().toNotificationRequest(),
            loading = false,
            messages = emptyList(),
        ),
    )
    val viewModelState: StateFlow<NotificationUiState> = _viewModelState

    fun setNotification(notification: Notification) {
        _viewModelState.update { it.copy(notificationRequest = notification.toNotificationRequest()) }
    }

    fun onMessageDismiss(id: Long) {
        val newMessages = viewModelState.value.messages.filter { it.id != id }
        _viewModelState.update { it.copy(messages = newMessages) }
    }

    fun onNotificationTitleChanged(newTitle: String) {
        _viewModelState.update {
            it.copy(
                notificationRequest = it.notificationRequest.copy(title = newTitle),
            )
        }
    }

    fun onNotificationDescriptionChanged(newDescription: String) {
        _viewModelState.update {
            it.copy(
                notificationRequest = it.notificationRequest.copy(content = newDescription),
            )
        }
    }

    fun addAttachment(newAttachment: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        _viewModelState.update {
            it.copy(
                notificationRequest = it.notificationRequest.copy(
                    attachment = newAttachment,
                    imageName = newAttachment.displayName,
                ),
            )
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun removeAttachment(newAttachment: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        _viewModelState.update {
            it.copy(
                notificationRequest = it.notificationRequest.copy(
                    attachment = null,
                    imageName = "",
                ),
            )
        }
    }

    fun saveNotification(notification: NotificationRequest) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val attachment = notification.attachment
        val domainAttachment =
            attachment?.getDomainAttachment(application, attachment.displayName.removeExtension())

        viewModelScope.launch {
            try {
                _viewModelState.update { it.copy(loading = true) }
                val value = if (notification.isNew) {
                    notificationRepository.createNotification(notification.toDomain(domainAttachment))
                } else {
                    notificationRepository.editNotification(notification.toDomain(domainAttachment))
                }

                val successMessage = Message.Localized.Informational(
                    id = UUID.randomUUID().mostSignificantBits,
                    title = "Éxito!",
                    message = "Notificación guardada con éxito!",
                    action = "Ok",
                )
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(successMessage)
                _viewModelState.update {
                    it.copy(
                        notificationRequest = value.toNotificationRequest(),
                        messages = messages,
                    )
                }
            } catch (ex: CancellationException) {
                return@launch
            } catch (ex: Exception) {
                logger.d("EditCreateNotificationViewModel.saveNotification", tr = ex)
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(errorParser(ex))
                _viewModelState.update { it.copy(messages = messages) }
            }
            _viewModelState.update { it.copy(loading = false) }
        }
    }
}
