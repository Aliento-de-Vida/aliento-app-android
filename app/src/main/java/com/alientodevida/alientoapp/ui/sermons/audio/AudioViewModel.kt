package com.alientodevida.alientoapp.ui.sermons.audio

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.UserFriendlyError
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.data.entities.network.base.ApiError
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch

class AudioViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val podcasts = repository.getCachedPodcasts()

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val _onError = MutableLiveData<UserFriendlyError>()
    val onError: LiveData<UserFriendlyError> = _onError

    fun refreshContent(isExpired: Boolean) {
        _isGettingData.value = true
        viewModelScope.launch {

            val result = getTokenOrError(isExpired)
            when (result) {
                is ApiResult.Success -> {
                    AppController.save(result.body, Token.key)
                }
                else -> {
                    _isGettingData.value = false
                    _onError.value = UserFriendlyError(result)
                    return@launch
                }
            }

            when (val podcastsResult = repository.refreshPodcasts(
                "Bearer ${result.body.accessToken}",
                Constants.PODCAST_ID
            )) {
                is ApiResult.Success -> {
                    _isGettingData.value = false
                }
                is ApiResult.ApiError -> {
                    if (podcastsResult.code == 401 && isExpired.not()) {
                        refreshContent(true)
                    } else {
                        _onError.value = UserFriendlyError(podcastsResult)
                        _isGettingData.value = false
                    }
                }
                else -> {
                    _onError.value = UserFriendlyError(podcastsResult)
                    _isGettingData.value = false
                }
            }
        }
    }

    private suspend fun getTokenOrError(isExpired: Boolean = false): ApiResult<Token, ApiError> {
        val token = AppController.get<Token>(Token.key)

        return if (isExpired || token == null) {
            when (val result = repository.getToken(Constants.SPOTIFY_TOKEN, Constants.SPOTIFY_GRANT_TYPE)) {
                is ApiResult.Success -> {
                    ApiResult.Success(result.body)
                }
                else -> {
                    result
                }
            }
        } else {
            ApiResult.Success(token)
        }
    }
}