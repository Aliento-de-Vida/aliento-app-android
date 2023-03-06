package com.alientodevida.alientoapp.app.features.donations

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsScreen
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.local.BankAccount
import com.alientodevida.alientoapp.domain.entities.local.DonationType
import com.alientodevida.alientoapp.domain.entities.local.PaymentItem
import com.alientodevida.alientoapp.domain.entities.local.Paypal
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class DonationsUiState(val paymentOptions: List<PaymentItem>)

@HiltViewModel
class DonationsViewModel @Inject constructor(
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
      analytics.logScreen(DonationsScreen())
  }

  private val _viewModelState = MutableStateFlow(
    DonationsUiState(listOf(
      PaymentItem(
        0,
        DonationType.OFRENDA,
        "Aliento de Vida AC",
        null,
        BankAccount(
          R.drawable.bbva_card,
          "BBVA BANCOMER",
          "4555 1130 0604 1497",
          "0113500640",
          "012910001135006409",
        ),
      ),
      PaymentItem(
        1,
        DonationType.OFRENDA,
        "Aliento de Vida AC",
        Paypal("https://www.paypal.com/paypalme/AlientoDeVidaMx"),
        null,
      ),
    ))
  )
  val viewModelState: StateFlow<DonationsUiState>
    get() = _viewModelState
  
}

data class DonationsScreen(
  override val name: String = "DonationsScreen"
): AnalyticsScreen()