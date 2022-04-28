package com.alientodevida.alientoapp.app.features.home

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.notification.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<HomeViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Home(
            viewModel = viewModel,
            goToHomeAdmin = { goToEditHome() },
            goToNotifications = { goToNotifications() },
            goToSettings = { goToSettings() },
            goToSermons = { goToSermons() },
            goToChurch = { goToChurch() },
            goToCampus = { goToCampus() },
            goToGallery = { goToGallery() },
            goToPrayer = { goToPrayer() },
            goToDonations = { goToDonations() },
            goToAdminLogin = { goToAdminLogin() },
          )
        }
      }
    }
  }
  
  private fun goToNotifications() {
    val action = HomeFragmentDirections.actionFragmentHomeToNotificationNavigation()
    findNavController().navigate(action)
  }
  
  private fun goToNotificationDetail(notification: Notification) {
    findNavController().navigate(
      HomeFragmentDirections.actionFragmentHomeToNotificationDetailFragment(
        notification
      )
    )
  }
  
  private fun goToGallery() {
    val action = HomeFragmentDirections.actionFragmentHomeToGalleriesFragment()
    findNavController().navigate(action)
  }
  
  private fun goToCampus() {
    val action = HomeFragmentDirections.actionFragmentHomeToCampusFragment()
    findNavController().navigate(action)
  }
  
  private fun goToSettings() {
    val action = HomeFragmentDirections.actionNavigationHomeToSettingsFragment()
    findNavController().navigate(action)
  }
  
  private fun goToSermons() {
    val action = HomeFragmentDirections.actionNavigationHomeToNavigationSermons()
    findNavController().navigate(action)
  }
  
  private fun goToChurch() {
    val action = HomeFragmentDirections.actionNavigationHomeToChurchFragment(viewModel.latestVideo)
    findNavController().navigate(action)
  }
  
  private fun goToDonations() {
    val action = HomeFragmentDirections.actionNavigationHomeToDonationsFragment()
    findNavController().navigate(action)
  }
  
  private fun goToPrayer() {
    val action = HomeFragmentDirections.actionNavigationHomeToPrayerFragment()
    findNavController().navigate(action)
  }
  
  private fun goToEditHome() {
    // TODO is this the right way to pass home ?
    viewModel.viewModelState.value.home?.let {
      val action = HomeFragmentDirections.actionFragmentHomeToEditHomeFragment(it)
      findNavController().navigate(action)
    }
  }
  
  private fun goToAdminLogin() {
    val action = HomeFragmentDirections.actionFragmentHomeToAdminNavigation()
    findNavController().navigate(action)
  }
  
}
