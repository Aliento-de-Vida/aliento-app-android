package com.alientodevida.alientoapp.admin.presentation.login

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.admin.domain.AdminRepository
import com.alientodevida.alientoapp.admin.presentation.logAdminLogin
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.state.ViewModelResult
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
            analytics.logAdminLogin()
        }
    }
}