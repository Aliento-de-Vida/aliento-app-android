package com.alientodevida.alientoapp.app.features.home.edit

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
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

data class HomeUiState(
  val home: Home,
  val loading: Boolean,
  val messages: List<Message>,
)

@HiltViewModel
class EditHomeViewModel @Inject constructor(
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
  
  private val initialHome: Home = savedStateHandle.get<Home>("home")!!
  
  private val _viewModelState = MutableStateFlow(
    HomeUiState(
      home = initialHome,
      loading = false,
      messages = emptyList(),
    )
  )
  val viewModelState: StateFlow<HomeUiState> = _viewModelState
  
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
    viewModelScope.launch {
      try {
        _viewModelState.update { it.copy(loading = true) }
        
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
  
}