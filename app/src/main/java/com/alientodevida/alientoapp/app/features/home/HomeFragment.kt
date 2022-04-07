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
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.openFacebookPage
import com.alientodevida.alientoapp.app.utils.extensions.openInstagramPage
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.app.utils.extensions.openTwitterPage
import com.alientodevida.alientoapp.app.utils.extensions.openYoutubeChannel
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
            goToEditHome = { goToEditHome() },
            goToNotifications = { goToNotifications() },
            goToNotificationDetail = { notification -> goToNotificationDetail(notification)},
            goToSettings = { goToSettings() },
            goToSermons = { goToSermons() },
            goToChurch = { goToChurch() },
            goToCampus = { goToCampus() },
            goToGallery = { goToGallery() },
            goToPrayer = { goToPrayer() },
            goToDonations = { goToDonations() },
            goToEbook = { goToEbook() },
            goToInstagram = { goToInstagram() },
            goToYoutube = { goToYoutube() },
            goToFacebook = { goToFacebook() },
            goToTwitter = { goToTwitter() },
            goToSpotify = { goToSpotify() },
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
  
  private fun goToEbook() {
    // TODO is this the right way to pass ebook ?
    viewModel.viewModelState.value.home?.ebook?.let {
      Utils.goToUrl(requireContext(), it)
    }
  }
  
  private fun goToEditHome() {
    // TODO is this the right way to pass home ?
    viewModel.viewModelState.value.home?.let {
      val action = HomeFragmentDirections.actionFragmentHomeToEditHomeFragment(it)
      findNavController().navigate(action)
    }
  }
  
  private fun goToInstagram() {
    viewModel.viewModelState.value.home?.socialMedia?.instagramUrl?.let { instagramUrl ->
      requireActivity().openInstagramPage(instagramUrl)
    }
  }
  
  private fun goToYoutube() {
    viewModel.viewModelState.value.home?.socialMedia?.youtubeChannelUrl?.let { youtubeChannelUrl ->
      requireActivity().openYoutubeChannel(youtubeChannelUrl)
    }
  }
  
  private fun goToFacebook() {
    viewModel.viewModelState.value.home?.socialMedia?.let { socialMedia ->
      requireActivity().openFacebookPage(socialMedia.facebookPageId, socialMedia.facebookPageUrl)
    }
  }
  
  private fun goToTwitter() {
    viewModel.viewModelState.value.home?.socialMedia?.let { socialMedia ->
      requireActivity().openTwitterPage(socialMedia.twitterUserId, socialMedia.twitterUrl)
    }
  }
  
  private fun goToSpotify() {
    viewModel.viewModelState.value.home?.socialMedia?.spotifyArtistId?.let { spotifyArtistId ->
      requireActivity().openSpotifyArtistPage(spotifyArtistId)
    }
  }
  
  private fun goToAdminLogin() {
    val action = HomeFragmentDirections.actionFragmentHomeToAdminNavigation()
    findNavController().navigate(action)
  }
  
}
