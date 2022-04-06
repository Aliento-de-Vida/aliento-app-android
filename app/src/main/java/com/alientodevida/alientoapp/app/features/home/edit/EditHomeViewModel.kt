package com.alientodevida.alientoapp.app.features.home.edit

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.compose.components.attachment.AttachmentModel
import com.alientodevida.alientoapp.app.compose.components.attachment.getDomainAttachment
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.extensions.addTimeStamp
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import com.alientodevida.alientoapp.domain.home.HomeImages as DomainHomeImages

data class HomeUiState(
  val home: Home,
  val images: HomeImages,
  val loading: Boolean,
  val messages: List<Message>,
)

data class HomeImages(
  val sermonsImage: AttachmentModel? = null,
  val churchImage: AttachmentModel? = null,
  val campusImage: AttachmentModel? = null,
  val galleriesImage: AttachmentModel? = null,
  val donationsImage: AttachmentModel? = null,
  val prayerImage: AttachmentModel? = null,
  val ebookImage: AttachmentModel? = null,
)

@HiltViewModel
class EditHomeViewModel @Inject constructor(
  private val homeRepository: HomeRepository,
  private val fileRepository: FileRepository,
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
  
  private val initialHome: Home = savedStateHandle.get<Home>("home")!!
  
  private val _viewModelState = MutableStateFlow(
    HomeUiState(
      home = initialHome,
      images = HomeImages(),
      loading = false,
      messages = emptyList(),
    )
  )
  val viewModelState: StateFlow<HomeUiState> = _viewModelState
  
  // Images
  fun addSermonsImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(sermonsImage = newImage)) }
  }

  fun removeSermonsImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(sermonsImage = null)) }
  }

  fun addChurchImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(churchImage = newImage)) }
  }

  fun removeChurchImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(churchImage = null)) }
  }

  fun addCampusImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(campusImage = newImage)) }
  }

  fun removeCampusImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(campusImage = null)) }
  }

  fun addGalleriesImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(galleriesImage = newImage)) }
  }

  fun removeGalleriesImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(galleriesImage = null)) }
  }
  
  fun addDonationsImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(donationsImage = newImage)) }
  }
  
  fun removeDonationsImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(donationsImage = null)) }
  }
  
  fun addPrayerImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(prayerImage = newImage)) }
  }
  
  fun removePrayerImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(prayerImage = null)) }
  }

  fun addEbookImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(ebookImage = newImage)) }
  }

  fun removeEbookImage(newImage: AttachmentModel) {
    _viewModelState.update { it.copy(images = it.images.copy(ebookImage = null)) }
  }
  
  // Home
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun onEbookChanged(newEbook: String) {
    _viewModelState.update { it.copy(home = it.home.copy(ebook = newEbook)) }
  }
  
  fun onYoutubePlaylistIdChanged(newYoutubePlaylistId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(youtubePlaylistId = newYoutubePlaylistId)) }
  }
  
  fun onYoutubeChannelIdChanged(newYoutubeChannelId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(youtubeChannelId = newYoutubeChannelId)) }
  }
  
  fun onSpotifyPlaylistIdChanged(newSpotifyPlaylistId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(spotifyPlaylistId = newSpotifyPlaylistId)) }
  }
  
  fun onPrayerEmailChanged(newPrayerEmail: String) {
    _viewModelState.update { it.copy(home = it.home.copy(prayerEmail = newPrayerEmail)) }
  }
  
  // Social Media
  fun onInstagramUrlChanged(newInstagramUrl: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(instagramUrl = newInstagramUrl))) }
  }
  
  fun onYoutubeChanelUrlChanged(newYoutubeChanelUrl: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(youtubeChannelUrl = newYoutubeChanelUrl))) }
  }
  
  fun onFacebookPageIdChanged(newFacebookPageId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(facebookPageId = newFacebookPageId))) }
  }
  
  fun onFacebookPageUrlChanged(newFacebookPageUrl: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(facebookPageUrl = newFacebookPageUrl))) }
  }
  
  fun onTwitterUserIdChanged(newTwitterUserId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(twitterUserId = newTwitterUserId))) }
  }
  
  fun onTwitterUrlChanged(newTwitterUrl: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(twitterUrl = newTwitterUrl))) }
  }
  
  fun onSpotifyArtistIdChanged(newSpotifyArtistId: String) {
    _viewModelState.update { it.copy(home = it.home.copy(socialMedia = it.home.socialMedia.copy(spotifyArtistId = newSpotifyArtistId))) }
  }
  
  fun save(home: Home) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
  
    viewModelScope.launch {
      try {
        _viewModelState.update { it.copy(loading = true) }
        
        updateImages(viewModelState.value.images)
        
        val value = homeRepository.updateHome(home)
        
        val successMessage = Message.Localized.Informational(
          id = UUID.randomUUID().mostSignificantBits,
          title = "Éxito!",
          message = "Datos guardados con éxito!",
          action = "Ok",
        )
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(successMessage)
        _viewModelState.update { it.copy(
          home = value,
          messages = messages
        ) }
        
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("EditHomeViewModel.save", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
  @RequiresApi(Build.VERSION_CODES.O)
  private suspend fun updateImages(images: HomeImages) {
    val imagesList = mutableListOf<Attachment?>()
    imagesList += images.sermonsImage?.getDomainAttachment(application, DomainHomeImages.SERMONS_IMAGE)
    imagesList += images.churchImage?.getDomainAttachment(application, DomainHomeImages.CHURCH_IMAGE)
    imagesList += images.campusImage?.getDomainAttachment(application, DomainHomeImages.CAMPUS_IMAGE)
    imagesList += images.galleriesImage?.getDomainAttachment(application, DomainHomeImages.GALLERY_IMAGE)
    imagesList += images.donationsImage?.getDomainAttachment(application, DomainHomeImages.DONATIONS_IMAGE)
    imagesList += images.prayerImage?.getDomainAttachment(application, DomainHomeImages.PRAYER_IMAGE)
    imagesList += images.ebookImage?.getDomainAttachment(application, DomainHomeImages.EBOOK_IMAGE)
    
    imagesList.filterNotNull().forEach {
      fileRepository.uploadImage(it.copy(name = it.name.addTimeStamp()))
    }
  }
  
}