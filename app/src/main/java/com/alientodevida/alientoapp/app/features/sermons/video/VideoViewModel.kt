package com.alientodevida.alientoapp.app.features.sermons.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
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
        _isGettingData.value = true
        viewModelScope.launch {
            val result = repository.refreshYoutubePlaylist(
                Constants.YOUTUBE_DEVELOPER_KEY,
                Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE
            )
            getCachedVideos()
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