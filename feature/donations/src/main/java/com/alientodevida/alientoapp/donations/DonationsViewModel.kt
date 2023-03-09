package com.alientodevida.alientoapp.donations

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.common.BankAccount
import com.alientodevida.alientoapp.domain.common.DonationType
import com.alientodevida.alientoapp.domain.common.PaymentItem
import com.alientodevida.alientoapp.domain.common.Paypal
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.base.BaseViewModel
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.ui.extensions.logScreenView
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
      analytics.logScreenView("donations_screen")
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