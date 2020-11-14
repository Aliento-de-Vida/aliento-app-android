package com.alientodevida.alientoapp.ui.audio

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.Podcast
import com.alientodevida.alientoapp.data.entities.Podcasts
import com.alientodevida.alientoapp.data.entities.Token
import kotlinx.coroutines.launch

private const val podcastId = "1gTd0GDz0PODVPYqhWmQjN"

class AudioViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _podcasts = MutableLiveData<List<Podcasts>>()
    val podcasts: LiveData<List<Podcasts>> = _podcasts

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    fun getContent() {

        _isGettingData.postValue(true)
        viewModelScope.launch {

            var token = AppController.get<Token>(Token.key)
            if (token == null) {
                token = repository.getToken()
                AppController.save(token, Token.key)
            }

            val result = repository.getPodcast("Bearer ${token.accessToken}", podcastId)
            _podcasts.postValue(result.items)
            _isGettingData.postValue(false)
        }
    }
}