package com.alientodevida.alientoapp.gallery.presentation.editcreate

import android.app.Application
import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.designsystem.components.attachment.getDomainAttachment
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.extensions.removeExtension
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.gallery.domain.GalleryRepository
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.state.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import com.alientodevida.alientoapp.domain.common.Attachment as DomainAttachment
import com.alientodevida.alientoapp.gallery.domain.GalleryRequest as DomainGalleryRequest

data class GalleryUiState(
    val galleryRequest: GalleryRequest,
    val loading: Boolean,
    val messages: List<Message>,
)

private fun GalleryRequest.toDomain(
    domainAttachment: DomainAttachment?,
    domainAttachmentLIst: List<DomainAttachment>,
) =
    DomainGalleryRequest(
        id = id,
        name = name,
        coverPicture = coverPicture,
        attachment = domainAttachment,
        images = images,
        attachmentList = domainAttachmentLIst,
    )

fun Gallery.toGalleryRequest() =
    GalleryRequest(
        id = id,
        name = name,
        coverPicture = coverPicture,
        attachment = null,
        images = images.map { it.name },
        attachmentList = emptyList(),
    )

@HiltViewModel
class EditCreateGalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
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
        GalleryUiState(
            galleryRequest = Gallery.empty().toGalleryRequest(),
            loading = false,
            messages = emptyList(),
        ),
    )
    val viewModelState: StateFlow<GalleryUiState> = _viewModelState

    fun setGallery(gallery: Gallery) {
        _viewModelState.update { it.copy(galleryRequest = gallery.toGalleryRequest()) }
    }

    fun onMessageDismiss(id: Long) {
        val newMessages = viewModelState.value.messages.filter { it.id != id }
        _viewModelState.update { it.copy(messages = newMessages) }
    }

    fun onNameChanged(newValue: String) {
        _viewModelState.update {
            it.copy(galleryRequest = it.galleryRequest.copy(name = newValue))
        }
    }

    fun addCoverAttachment(newValue: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        _viewModelState.update {
            it.copy(
                galleryRequest = it.galleryRequest.copy(
                    attachment = newValue,
                    coverPicture = newValue.displayName,
                ),
            )
        }
    }

    fun removeCoverAttachment(value: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        _viewModelState.update {
            it.copy(galleryRequest = it.galleryRequest.copy(attachment = null, coverPicture = ""))
        }
    }

    fun addAttachmentToList(newValue: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        val attachments = viewModelState.value.galleryRequest.attachmentList.toMutableList()
        attachments += newValue
        _viewModelState.update {
            it.copy(galleryRequest = it.galleryRequest.copy(attachmentList = attachments))
        }
    }

    fun removeAttachmentFromList(value: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) {
        val attachments = viewModelState.value.galleryRequest.attachmentList.toMutableList()
        attachments.removeAll { it.displayName == value.displayName }
        _viewModelState.update {
            it.copy(galleryRequest = it.galleryRequest.copy(attachmentList = attachments))
        }
    }

    fun removeImage(value: String) {
        val images = viewModelState.value.galleryRequest.images.toMutableList()
        images.removeAll { it == value }
        _viewModelState.update {
            it.copy(galleryRequest = it.galleryRequest.copy(images = images))
        }
    }

    fun saveGallery(gallery: GalleryRequest) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val attachment = gallery.attachment
        val domainAttachment =
            attachment?.getDomainAttachment(application, attachment.displayName.removeExtension())

        viewModelScope.launch {
            try {
                _viewModelState.update { it.copy(loading = true) }
                val value = if (gallery.isNew) {
                    val domainAttachments = gallery.attachmentList.map {
                        it.getDomainAttachment(application, it.displayName.removeExtension())
                    }.filterNotNull()
                    galleryRepository.createGallery(
                        gallery.toDomain(
                            domainAttachment,
                            domainAttachments,
                        ),
                    )
                } else {
                    val domainAttachments = gallery.attachmentList.map {
                        it.getDomainAttachment(application, it.displayName.removeExtension())
                    }.filterNotNull()
                    galleryRepository.editGallery(
                        gallery.toDomain(
                            domainAttachment,
                            domainAttachments,
                        ),
                    )
                }

                val successMessage = Message.Localized.Informational(
                    id = UUID.randomUUID().mostSignificantBits,
                    title = "Éxito!",
                    message = "Galería guardada con éxito!",
                    action = "Ok",
                )
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(successMessage)
                _viewModelState.update {
                    it.copy(
                        galleryRequest = value.toGalleryRequest(),
                        messages = messages,
                    )
                }
            } catch (ex: CancellationException) {
                return@launch
            } catch (ex: Exception) {
                logger.d("EditCreateNotificationViewModel.saveGallery", tr = ex)
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(errorParser(ex))
                _viewModelState.update { it.copy(messages = messages) }
            }
            _viewModelState.update { it.copy(loading = false) }
        }
    }
}
