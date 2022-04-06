package com.alientodevida.alientoapp.app.features.settings

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.gallery.GalleryRepository
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val firebaseMessaging: FirebaseMessaging,
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
  val isDarkTheme = preferences.isDarkThemeFlow
  val areNotificationsEnabled = preferences.pushEnabledFlow
  
  fun onDarkThemeChanged(newValue: Boolean) {
    preferences.isDarkTheme = newValue
  }
  
  fun onPushNotificationsChanged(newValue: Boolean) {
    if (newValue)
      firebaseMessaging.subscribeToTopic("push_notifications")
    else
      firebaseMessaging.unsubscribeFromTopic("push_notifications")
    
    preferences.pushEnabled = newValue
  }
  
}