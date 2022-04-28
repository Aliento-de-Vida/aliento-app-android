package com.alientodevida.alientoapp.app.features.campus.list

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.campus.Campus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampusesFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<CampusesViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Campuses(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            goToEditCampus = { campus -> goToEditCampus(campus)},
            goToCreateCampus = { goToCreateCampus() },
          )
        }
      }
    }
  }
  
  private fun goToCampus(campus: Campus) {
    findNavController().navigate(
      CampusesFragmentDirections.actionFragmentCampusToCampusDetailFragment(
        campus
      )
    )
  }
  
  private fun goToEditCampus(campus: Campus) {
    findNavController().navigate(
      CampusesFragmentDirections.actionFragmentCampusToEditCreateCampusFragment(
        campus
      )
    )
  }
  
  private fun goToCreateCampus() {
    findNavController().navigate(
      CampusesFragmentDirections.actionFragmentCampusToEditCreateCampusFragment(null)
    )
  }
  
}