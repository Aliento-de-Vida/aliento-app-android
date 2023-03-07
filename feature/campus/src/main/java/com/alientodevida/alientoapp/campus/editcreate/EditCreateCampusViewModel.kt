package com.alientodevida.alientoapp.campus.editcreate

import android.app.Application
import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.components.attachment.AttachmentModel
import com.alientodevida.alientoapp.ui.components.attachment.getDomainAttachment
import com.alientodevida.alientoapp.ui.state.Message
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.campus.CampusRepository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.extensions.removeExtension
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import com.alientodevida.alientoapp.domain.campus.CampusRequest as DomainCampusRequest
import com.alientodevida.alientoapp.domain.common.Attachment as DomainAttachment

data class CampusUiState(
    val campusRequest: CampusRequest,
    val loading: Boolean,
    val messages: List<Message>,
)

private fun CampusRequest.toDomain(
  domainAttachment: DomainAttachment?,
  domainAttachmentLIst: List<DomainAttachment>
) =
  DomainCampusRequest(
    id = id,
    name = name,
    description = description,
    shortDescription = shortDescription,
    location = location,
    contact = contact,
    imageName = imageName,
    attachment = domainAttachment,
    videoUrl = videoUrl,
    images = images,
    attachmentList = domainAttachmentLIst,
  )

fun Campus.toCampusRequest() =
  CampusRequest(
    id = id,
    name = name,
    description = description,
    shortDescription = shortDescription,
    location = location,
    contact = contact,
    imageName = imageUrl,
    attachment = null,
    videoUrl = videoUrl,
    images = images,
    attachmentList = emptyList(),
  )

@HiltViewModel
class EditCreateNotificationViewModel @Inject constructor(
  private val campusRepository: CampusRepository,
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
    CampusUiState(
      campusRequest = Campus.empty().toCampusRequest(),
      loading = false,
      messages = emptyList(),
    )
  )
  val viewModelState: StateFlow<CampusUiState> = _viewModelState
  
  fun setCampus(campus: Campus) {
    _viewModelState.update { it.copy(campusRequest = campus.toCampusRequest()) }
  }
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun onNameChanged(newValue: String) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(name = newValue))
    }
  }
  
  fun onDescriptionChanged(newValue: String) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(description = newValue))
    }
  }
  
  fun onShortDescriptionChanged(newValue: String) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(shortDescription = newValue))
    }
  }
  
  fun onLatitudeChanged(newValue: String) {
    _viewModelState.update {
      it.copy(
        campusRequest = it.campusRequest.copy(
          location = it.campusRequest.location.copy(
            latitude = newValue
          )
        )
      )
    }
  }
  
  fun onLongitudeChanged(newValue: String) {
    _viewModelState.update {
      it.copy(
        campusRequest = it.campusRequest.copy(
          location = it.campusRequest.location.copy(
            longitude = newValue
          )
        )
      )
    }
  }
  
  fun onContactChanged(newValue: String) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(contact = newValue))
    }
  }
  
  fun onVideoUrlChanged(newValue: String) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(videoUrl = newValue))
    }
  }
  
  fun addCoverAttachment(newValue: AttachmentModel) {
    _viewModelState.update {
      it.copy(
        campusRequest = it.campusRequest.copy(
          attachment = newValue,
          imageName = newValue.displayName,
        )
      )
    }
  }

  @Suppress("UNUSED_PARAMETER")
  fun removeCoverAttachment(value: AttachmentModel) {
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(attachment = null, imageName = ""))
    }
  }
  
  fun addAttachmentToList(newValue: AttachmentModel) {
    val attachments = viewModelState.value.campusRequest.attachmentList.toMutableList()
    attachments += newValue
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(attachmentList = attachments))
    }
  }
  
  fun removeAttachmentFromList(value: AttachmentModel) {
    val attachments = viewModelState.value.campusRequest.attachmentList.toMutableList()
    attachments.removeAll { it.displayName == value.displayName }
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(attachmentList = attachments))
    }
  }
  
  fun removeImage(value: String) {
    val images = viewModelState.value.campusRequest.images.toMutableList()
    images.removeAll { it == value }
    _viewModelState.update {
      it.copy(campusRequest = it.campusRequest.copy(images = images))
    }
  }
  
  fun saveCampus(campus: CampusRequest) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    
    val attachment = campus.attachment
    val domainAttachment =
      attachment?.getDomainAttachment(application, attachment.displayName.removeExtension())
    
    viewModelScope.launch {
      try {
        _viewModelState.update { it.copy(loading = true) }
        val value = if (campus.isNew) {
          val domainAttachments = campus.attachmentList.map {
            it.getDomainAttachment(application, it.displayName.removeExtension())
          }.filterNotNull()
          campusRepository.createCampus(campus.toDomain(domainAttachment, domainAttachments))
        } else {
          val domainAttachments = campus.attachmentList.map {
            it.getDomainAttachment(application, it.displayName.removeExtension())
          }.filterNotNull()
          campusRepository.editCampus(campus.toDomain(domainAttachment, domainAttachments))
        }
        
        val successMessage = Message.Localized.Informational(
          id = UUID.randomUUID().mostSignificantBits,
          title = "Éxito!",
          message = "Campus guardado con éxito!",
          action = "Ok",
        )
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(successMessage)
        _viewModelState.update {
          it.copy(
            campusRequest = value.toCampusRequest(),
            messages = messages
          )
        }
        
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("EditCreateNotificationViewModel.saveCampus", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
}