package com.alientodevida.alientoapp.app.ui.sermons.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.AppController
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.entities.UserFriendlyError
import com.alientodevida.alientoapp.domain.entities.network.Token
import com.alientodevida.alientoapp.domain.entities.network.base.ApiError
import com.alientodevida.alientoapp.domain.entities.network.base.ApiResult
import com.alientodevida.alientoapp.domain.entities.network.base.ResponseError
import com.alientodevida.alientoapp.app.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val podcasts = repository.getCachedPodcasts()

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val _onError = MutableLiveData<UserFriendlyError?>()
    val onError: LiveData<UserFriendlyError?> = _onError

    fun errorHandled() {
        _onError.value = null
    }

    fun refreshContent(isExpired: Boolean) {
        _isGettingData.value = true
        viewModelScope.launch {

            val result = getTokenOrError(isExpired)
            when (result) {
                is ApiResult.Success -> {
                    AppController.save(result.body, Token.key)
                }
                is ApiResult.Failure -> {
                    _isGettingData.value = false
                    _onError.value =
                        UserFriendlyError(result.responseError)
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
                is ApiResult.Failure -> {

                    if ((podcastsResult.responseError is ResponseError.ApiResponseError) && (podcastsResult.responseError as ResponseError.ApiResponseError<ApiError>).code == 401 && isExpired.not()) {
                        refreshContent(true)
                    } else {
                        _onError.value =
                            UserFriendlyError(podcastsResult.responseError)
                        _isGettingData.value = false
                    }
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