package com.alientodevida.alientoapp.ui.church

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.utils.Constants.US_VIDEO

class ChurchViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    val usImageUrl: String = "https://img.youtube.com/vi/$US_VIDEO/hqdefault.jpg"

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    init {

    }
}