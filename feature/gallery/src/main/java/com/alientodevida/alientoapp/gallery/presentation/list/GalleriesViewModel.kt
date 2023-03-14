package com.alientodevida.alientoapp.gallery.presentation.list

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.gallery.domain.GalleryRepository
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.extensions.logScreenView
import com.alientodevida.alientoapp.ui.state.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GalleriesUiState(
    val galleries: List<Gallery> = emptyList(),
    val loading: Boolean = true,
    val messages: List<Message> = emptyList(),
)

@HiltViewModel
class GalleriesViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    coroutineDispatchers: CoroutineDispatchers,
    errorParser: ErrorParser,
    logger: Logger,
    preferences: Preferences,
    savedStateHandle: SavedStateHandle,
    application: Application,
    analytics: Analytics,
) : BaseViewModel(
    coroutineDispatchers,
    errorParser,
    logger,
    preferences,
    savedStateHandle,
    application,
) {

    init {
        analytics.logScreenView("galleries_screen")
    }

    val isAdmin = preferences.isAdminFlow

    private val _viewModelState = MutableStateFlow(GalleriesUiState())
    val viewModelState: StateFlow<GalleriesUiState> = _viewModelState

    fun onMessageDismiss(id: Long) {
        val newMessages = viewModelState.value.messages.filter { it.id != id }
        _viewModelState.update { it.copy(messages = newMessages) }
    }

    fun getGalleries() {
        viewModelScope.launch {
            _viewModelState.update { it.copy(loading = true) }
            try {
                val galleries = galleryRepository.getGalleries()
                _viewModelState.update { it.copy(galleries = galleries) }
            } catch (ex: CancellationException) {
                return@launch
            } catch (ex: Exception) {
                logger.d("GalleriesViewModel.getGalleries", tr = ex)
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(errorParser(ex))
                _viewModelState.update { it.copy(messages = messages) }
            }
            _viewModelState.update { it.copy(loading = false) }
        }
    }

    fun deleteGallery(gallery: Gallery) {
        viewModelScope.launch {
            _viewModelState.update { it.copy(loading = true) }
            try {
                galleryRepository.deleteGallery(gallery.id)
                val galleries = viewModelState.value.galleries.filter { it.id != gallery.id }
                _viewModelState.update { it.copy(galleries = galleries) }
            } catch (ex: CancellationException) {
                return@launch
            } catch (ex: Exception) {
                logger.d("GalleriesViewModel.deleteGallery", tr = ex)
                val messages = viewModelState.value.messages.toMutableList()
                messages.add(errorParser(ex))
                _viewModelState.update { it.copy(messages = messages) }
            }
            _viewModelState.update { it.copy(loading = false) }
        }
    }
}