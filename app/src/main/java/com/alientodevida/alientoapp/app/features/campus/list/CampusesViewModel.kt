package com.alientodevida.alientoapp.app.features.campus.list

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.campus.CampusRepository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CampusesUiState(
  val campuses: List<Campus>,
  val loading: Boolean,
  val messages: List<Message>,
)

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
  private val _viewModelState = MutableStateFlow(CampusesUiState(emptyList(), false, emptyList()))
  val viewModelState: StateFlow<CampusesUiState> = _viewModelState
  
  val isAdmin = preferences.isAdminFlow
  
  fun getCampuses() {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        val campuses = campusRepository.getCampus()
        _viewModelState.update { it.copy(campuses = campuses) }
      
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
  fun deleteCampus(campus: Campus) {
    viewModelScope.launch {
      _viewModelState.update { it.copy(loading = true) }
      try {
        campusRepository.deleteNotification(campus.id)
        val campuses = viewModelState.value.campuses.filter { it.id != campus.id }
        _viewModelState.update { it.copy(campuses = campuses) }
        
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
}