package com.alientodevida.alientoapp.app.features.gallery.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.gallery.GalleryRepository
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleriesViewModel @Inject constructor(
  private val galleryRepository: GalleryRepository,
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
  private val _galleries = MutableLiveData<ViewModelResult<List<Gallery>>>()
  val galleries: LiveData<ViewModelResult<List<Gallery>>> = _galleries
  
  init {
    getGalleries()
  }
  
  private fun getGalleries() {
    liveDataResult(_galleries) { galleryRepository.getGalleries() }
  }
}