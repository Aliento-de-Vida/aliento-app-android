package com.alientodevida.alientoapp.ui.church

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.UserFriendlyError
import com.alientodevida.alientoapp.data.entities.network.Transmision
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult
import com.alientodevida.alientoapp.utils.Constants.US_VIDEO
import kotlinx.coroutines.launch

class ChurchViewModel @ViewModelInject constructor(
    private val repository: Repository,
): ViewModel() {

    val usImageUrl: String = "https://img.youtube.com/vi/$US_VIDEO/hqdefault.jpg"

    private val _transmission = MutableLiveData<Transmision>()
    val transmission: LiveData<Transmision> = _transmission

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val _onError = MutableLiveData<UserFriendlyError?>()
    val onError: LiveData<UserFriendlyError?> = _onError

    init {
        getTransmission()
    }

    fun errorHandled() {
        _onError.value = null
    }

    private fun getTransmission() {
        _isGettingData.value = true
        viewModelScope.launch {

            when (val result = repository.getTransmision()) {
                is ApiResult.Success -> {
                    _transmission.value = result.body
                }
                is ApiResult.Failure -> {
                    _onError.value = UserFriendlyError(result.responseError)
                }
            }

            _isGettingData.value = false
        }
    }
}