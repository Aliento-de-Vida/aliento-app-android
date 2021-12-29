package com.alientodevida.alientoapp.app.features.gallery

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
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
  val images = listOf(
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
    Image("aliento_de_vida.jpeg"),
    Image("cursos.png"),
  )
}