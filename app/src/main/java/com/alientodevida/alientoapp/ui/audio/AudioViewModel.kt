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
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

            var token = getToken()

            try {
                getPodcasts(token)
            } catch (ex: HttpException) {
                if (ex.code() == 401) {
                    token = getToken(true)
                    getPodcasts(token)
                }
            }
        }
    }

    private suspend fun getPodcasts(token: Token) {
        val result = repository.getPodcast("Bearer ${token.accessToken}", Constants.PODCAST_ID)
        _podcasts.postValue(result.items)
        _isGettingData.postValue(false)
    }

    private suspend fun getToken(isExpired: Boolean = false): Token {
        var token = AppController.get<Token>(Token.key)
        if (token == null || isExpired) {
            token = repository.getToken()
            AppController.save(token, Token.key)
        }
        return token
    }
}