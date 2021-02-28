package com.alientodevida.alientoapp.ui.sermons

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.CarrouselItem
import com.alientodevida.alientoapp.data.entities.local.CarrouselItemType
import kotlinx.coroutines.launch
import retrofit2.HttpException


class SermonsViewModel @ViewModelInject constructor(
): ViewModel() {
}