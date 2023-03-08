package com.alientodevida.alientoapp.sermons.presentation.video

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.state.Message
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.ui.extensions.logScreenView
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.common.Home
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VideoSermonsUiState(
  val home: Home?,
  val videos: List<YoutubeVideo> = emptyList(),
  val loading: Boolean = true,
  val messages: List<Message> = emptyList(),
)

@HiltViewModel
class VideoViewModel @Inject constructor(
  private val videoRepository: VideoRepository,
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
    analytics.logScreenView("video_sermons_screen")
  }

  private val _viewModelState = MutableStateFlow(VideoSermonsUiState(home = preferences.home))
  val viewModelState: StateFlow<VideoSermonsUiState> = _viewModelState
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun refreshContent() {
    preferences.home?.youtubeChannelId?.let {
      viewModelScope.launch {
        _viewModelState.update { it.copy(loading = true) }
        try {
          val videos = videoRepository.getYoutubeChannelVideos(it)
          _viewModelState.update { it.copy(videos = videos) }
        } catch (ex: CancellationException) {
          return@launch
        } catch (ex: Exception) {
          logger.e("VideoViewModel.refreshContent()", tr = ex)
          val messages = viewModelState.value.messages.toMutableList()
          messages.add(errorParser(ex))
          _viewModelState.update { it.copy(messages = messages) }
        }
        _viewModelState.update { it.copy(loading = false) }
      }
    }
  }
  
  fun getCachedVideos() {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        val videos = videoRepository.getCachedVideos()
        _viewModelState.update { it.copy(videos = videos) }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.e("VideoViewModel.getCachedVideos()", tr = ex)
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
}