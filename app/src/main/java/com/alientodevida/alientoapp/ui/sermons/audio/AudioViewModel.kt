package com.alientodevida.alientoapp.ui.sermons.audio

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AudioViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val podcasts = repository.getPodcasts()

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    fun refreshContent() {
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
         repository.refreshPodcasts("Bearer ${token.accessToken}", Constants.PODCAST_ID)
        _isGettingData.postValue(false)
    }

    private suspend fun getToken(isExpired: Boolean = false): Token {
        var token = AppController.get<Token>(Token.key)
        if (token == null || isExpired) {
            token = repository.getToken(Constants.SPOTIFY_TOKEN, Constants.SPOTIFY_GRANT_TYPE)
            AppController.save(token, Token.key)
        }
        return token
    }
}