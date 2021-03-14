package com.alientodevida.alientoapp.ui.church

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.network.CsrfToken
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.data.entities.network.Transmision
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Constants.US_VIDEO
import kotlinx.coroutines.launch

class ChurchViewModel @ViewModelInject constructor(
    private val repository: Repository,
): ViewModel() {

    val usImageUrl: String = "https://img.youtube.com/vi/$US_VIDEO/hqdefault.jpg"

    private val _transmision = MutableLiveData<Transmision>()
    val transmision: LiveData<Transmision> = _transmision

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    init {
        getTransmision()
    }

    private fun getTransmision() {
        _isGettingData.value = true
        viewModelScope.launch {
            val transmision = repository.getTransmision()
            _isGettingData.value = false
            _transmision.value = transmision
        }
    }
}