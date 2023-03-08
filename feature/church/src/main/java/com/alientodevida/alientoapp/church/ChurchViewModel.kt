package com.alientodevida.alientoapp.church

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.extensions.logScreenView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChurchViewModel @Inject constructor(
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
      analytics.logScreenView("church_screen")
  }

  val latestVideo = savedStateHandle.get<YoutubeVideo>("latest_video")

}