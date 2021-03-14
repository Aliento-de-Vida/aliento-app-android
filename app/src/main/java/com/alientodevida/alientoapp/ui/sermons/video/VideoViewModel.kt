package com.alientodevida.alientoapp.ui.sermons.video

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.utils.Constants
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

    init {
        getCachedVideos()
    }

    fun refreshContent() {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                _videos.value = repository.refreshYoutubePlaylist(Constants.YOUTUBE_DEVELOPER_KEY, Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)
                getCachedVideos()
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }

    /**
     * Sermon items
     */
    private fun getCachedVideos() {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                val sermons = async(Dispatchers.IO) {
                    repository.getYoutubePlaylist()
                }

                _videos.value = sermons.await()
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }
}