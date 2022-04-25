package com.alientodevida.alientoapp.app.features.donations

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.PaymentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationsFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<DonationsViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Donations(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            onCardClick = { item -> onCardClick(item)},
          )
        }
      }
    }
  }
  
  private fun onCardClick(item: PaymentItem) {
    when {
      item.paypal != null -> Utils.goToUrl(requireContext(), item.paypal!!.url)
      item.bankAccount != null -> {
        Utils.copyToClipboard(
          context = requireContext(),
          name = "Número de tarjeta",
          value = item.bankAccount!!.cardNumber
        )
      }
    }
  }
}

























