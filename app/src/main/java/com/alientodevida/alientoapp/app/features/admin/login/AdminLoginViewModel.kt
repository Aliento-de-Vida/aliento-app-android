package com.alientodevida.alientoapp.app.features.admin.login

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.admin.AdminRepository
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsEvent
import com.alientodevida.alientoapp.domain.analytics.AnalyticsScreen
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
  private val adminRepository: AdminRepository,
  private val analytics: Analytics,
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
  
  private val _loginResult = MutableStateFlow<ViewModelResult<Unit>?>(null)
  val loginResult: StateFlow<ViewModelResult<Unit>?> = _loginResult
  
  fun login(email: String, password: String) {
    stateFlowNullableResult(
      stateFlow = _loginResult,
    ) {
      val token = adminRepository.login(email, password)
      preferences.adminToken = token
      analytics.logEvent(AdminLoginEvent())
    }
  }
  
}

data class AdminLoginEvent(
  override val name: String = "AdminLoginEvent"
): AnalyticsEvent()