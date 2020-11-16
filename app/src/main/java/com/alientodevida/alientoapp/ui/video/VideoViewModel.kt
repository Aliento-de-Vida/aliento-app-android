package com.alientodevida.alientoapp.ui.video

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VideoViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel() {

    val videos = repository.getYoutubePlaylist()

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    fun refreshContent() {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                repository.refreshYoutubePlaylist(Constants.YOUTUBE_DEVELOPER_KEY, Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }
}