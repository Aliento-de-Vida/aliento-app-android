package com.alientodevida.alientoapp.app.features.sermons.video

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.video.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
  private val videoRepository: VideoRepository,
  coroutineDispatchers: CoroutineDispatchers,
  errorParser: ErrorParser,
  logger: Logger,
  preferences: Preferences,
  savedStateHandle: SavedStateHandle,
  application: Application,
) : BaseViewModel(
  coroutineDispatchers,
  errorParser,
  logger,
  preferences,
  savedStateHandle,
  application,
) {
  val home = preferences.home
  
  private val _videos = MutableLiveData<ViewModelResult<List<YoutubeVideo>>>()
  val videos: LiveData<ViewModelResult<List<YoutubeVideo>>>
    get() = _videos
  
  init {
    getCachedVideos()
  }
  
  fun refreshContent() {
    home?.youtubeChannelId?.let {
      liveDataResult(_videos) {
        videoRepository.getYoutubeChannelVideos(it)
      }
    }
  }
  
  private fun getCachedVideos() {
    liveDataResult(
      liveData = _videos,
      dispatcher = coroutineDispatchers.io
    ) {
      videoRepository.getCachedVideos()
    }
  }
}