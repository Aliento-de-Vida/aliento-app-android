package com.alientodevida.alientoapp.app.features.sermons.audio

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.AppController
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.network.Token
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    errorParser: ErrorParser,
    logger: Logger,
    preferences: Preferences,
    savedStateHandle: SavedStateHandle,
    application: Application,
) : BaseViewModel(
    coroutineDispatchers,
    errorParser,
    logger,
    preferences,
    savedStateHandle,
    application,
) {
    val podcasts = repository.getCachedPodcasts()

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    fun refreshContent(isExpired: Boolean) {
        _isGettingData.value = true
        viewModelScope.launch {

            val result = getTokenOrError(isExpired)
            preferences.jwtToken = result

            try {
                val podcastsResult = repository.refreshPodcasts(
                    "Bearer ${result.accessToken}",
                    Constants.PODCAST_ID
                )
                _isGettingData.value = false

            } catch (ex: Exception) {
                    refreshContent(true)
            }
        }
    }

    private suspend fun getTokenOrError(isExpired: Boolean = false): Token {
        val token = preferences.jwtToken

        return if (isExpired || token == null) {
            repository.getToken(Constants.SPOTIFY_TOKEN, Constants.SPOTIFY_GRANT_TYPE)
        } else {
            token
        }
    }
}