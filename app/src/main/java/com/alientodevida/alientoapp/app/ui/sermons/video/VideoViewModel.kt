package com.alientodevida.alientoapp.app.ui.sermons.video

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.entities.UserFriendlyError
import com.alientodevida.alientoapp.domain.entities.network.base.ApiResult
import com.alientodevida.alientoapp.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VideoViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel() {

    private val _videos = MutableLiveData<List<YoutubePlaylistItemEntity>>()
    val videos: LiveData<List<YoutubePlaylistItemEntity>>
        get() = _videos

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val _onError = MutableLiveData<UserFriendlyError?>()
    val onError: LiveData<UserFriendlyError?> = _onError

    init {
        getCachedVideos()
    }

    fun errorHandled() {
        _onError.value = null
    }

    fun refreshContent() {
        _isGettingData.value = true
        viewModelScope.launch {
            when (
                val result = repository.refreshYoutubePlaylist(
                    Constants.YOUTUBE_DEVELOPER_KEY,
                    Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE
                )) {

                is ApiResult.Success -> getCachedVideos()
                is ApiResult.Failure -> {
                    _onError.value =
                        UserFriendlyError(result.responseError)
                }
            }
            _isGettingData.value = false
        }
    }

    /**
     * Sermon items
     */
    private fun getCachedVideos() {
        _isGettingData.value = true
        viewModelScope.launch {
            try {
                val sermons = async(Dispatchers.IO) {
                    repository.getCachedYoutubePlaylist()
                }

                _videos.value = sermons.await()
                _isGettingData.value = false

            } catch (ex: HttpException) {
                _isGettingData.value = false
                ex.printStackTrace()
            }
        }
    }
}