package com.alientodevida.alientoapp.app.features.sermons.audio

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.local.Audio
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.spotify.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
  private val spotifyRepository: SpotifyRepository,
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
  
  private val _podcasts = MutableLiveData<ViewModelResult<List<Audio>>>()
  val podcasts = _podcasts
  
  init {
    getPodcasts()
  }
  
  private fun getPodcasts() {
    liveDataResult(
      _podcasts,
      dispatcher = coroutineDispatchers.io
    ) {
      spotifyRepository.getCachedPodcasts()
    }
  }
  
  fun refreshContent() {
    preferences.home?.spotifyPlaylistId?.let {
      liveDataResult(_podcasts) {
        spotifyRepository.refreshAudios("4VYxusCiKsWxcfUveymGU5")
      }
    }
  }
}