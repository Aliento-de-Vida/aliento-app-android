package com.alientodevida.alientoapp.sermons.audio

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.state.Message
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.ui.extensions.logScreenView
import com.alientodevida.alientoapp.domain.audio.Audio
import com.alientodevida.alientoapp.domain.audio.SpotifyRepository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AudioSermonsUiState(
    val home: Home?,
    val audios: List<Audio> = emptyList(),
    val loading: Boolean = true,
    val messages: List<Message> = emptyList(),
)

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val spotifyRepository: SpotifyRepository,
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
      analytics.logScreenView("audio_sermons_screen")
  }

  private val _viewModelState = MutableStateFlow(AudioSermonsUiState(home = preferences.home))
  val viewModelState: StateFlow<AudioSermonsUiState> = _viewModelState
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun getCachedPodcasts() {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        val audios = spotifyRepository.getCachedPodcasts()
        _viewModelState.update { it.copy(audios = audios) }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.e("AudioViewModel.getCachedPodcasts()", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
  
      if (viewModelState.value.audios.isEmpty()) refreshContent()
    }
  }
  
  fun refreshContent() {
    preferences.home?.socialMedia?.spotifyArtistId?.let {
      viewModelScope.launch {
        _viewModelState.update { it.copy(loading = true) }
        try {
          val audios = spotifyRepository.refreshAudios(it)
          _viewModelState.update { it.copy(audios = audios) }
        } catch (ex: CancellationException) {
          return@launch
        } catch (ex: Exception) {
          logger.e("AudioViewModel.refreshContent()", tr = ex)
          val messages = viewModelState.value.messages.toMutableList()
          messages.add(errorParser(ex))
          _viewModelState.update { it.copy(messages = messages) }
        }
        _viewModelState.update { it.copy(loading = false) }
      }
    }
  }
}