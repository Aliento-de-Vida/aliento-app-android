package com.alientodevida.alientoapp.app.features.sermons.video

import android.app.Application
import androidx.lifecycle.*
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.youtube.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
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

    private val _videos = MutableLiveData<ViewModelResult<List<YoutubePlaylistItemEntity>>>()
    val videos: LiveData<ViewModelResult<List<YoutubePlaylistItemEntity>>>
        get() = _videos

    init {
        getCachedVideos()
    }

    fun refreshContent() {
        liveDataResult(_videos) {
            youtubeRepository.refreshYoutubePlaylist(Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)
        }
    }

    private fun getCachedVideos() {
        liveDataResult(
            liveData = _videos,
            dispatcher = coroutineDispatchers.io
        ) {
            youtubeRepository.getCachedYoutubePlaylist()
        }
    }
}