package com.alientodevida.alientoapp.ui.video

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.presentation.VideoInfo
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VideoViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel() {

    private val _videos = MutableLiveData<List<VideoInfo>>()
    val videos: LiveData<List<VideoInfo>> = _videos

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData


    fun updateData() {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                val playlist = repository.getYoutubePlayList(Constants.YOUTUBE_DEVELOPER_KEY, Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)

                val videosData = ArrayList<VideoInfo>()
                playlist.items.forEach {
                    videosData.add(VideoInfo(
                        it.snippet.title,
                        it.snippet.resourceId.videoId,
                        it.snippet.description,
                        it.snippet.publishedAt,
                        it.snippet.thumbnails.high.url
                    ))
                }

                _videos.postValue(videosData)
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }
}