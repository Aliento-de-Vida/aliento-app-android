package com.alientodevida.alientoapp.settings

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.extensions.logScreenView
import com.alientodevida.alientoapp.ui.messaging.NotificationsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationsManager: NotificationsManager,
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
        analytics.logScreenView("settings_screen")
    }

    val isDarkTheme = preferences.isDarkThemeFlow
    val areNotificationsEnabled = preferences.pushEnabledFlow

    fun onDarkThemeChanged(newValue: Boolean) {
        preferences.isDarkTheme = newValue
    }

    fun onPushNotificationsChanged(enabled: Boolean) {
        notificationsManager.subscribeToPushNotifications(subscribe = enabled)
        preferences.pushEnabled = enabled
    }
}