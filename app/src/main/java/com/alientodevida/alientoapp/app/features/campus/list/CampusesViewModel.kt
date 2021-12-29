package com.alientodevida.alientoapp.app.features.campus.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.campus.CampusRepository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampusesViewModel @Inject constructor(
  private val campusRepository: CampusRepository,
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
  private val _campus = MutableLiveData<ViewModelResult<List<Campus>>>()
  val campus: LiveData<ViewModelResult<List<Campus>>> = _campus
  
  init {
    getCampus()
  }
  
  private fun getCampus() {
    liveDataResult(_campus) {
      campusRepository.getCampus()
    }
  }
}