package com.alientodevida.alientoapp.home.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.home.Home
import com.alientodevida.alientoapp.ui.navigation.MainActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

fun NavGraphBuilder.home(actions: MainActions) {
  composable(MainDestination.Home.path) {
    Home(
      viewModel = hiltViewModel(),
      goToHomeAdmin = actions::navigateToAdminHome,
      goToNotifications = actions::navigateToNotifications,
      goToSettings = actions::navigateToSettings,
      goToSermons = actions::navigateToSermons,
      goToChurch = actions::navigateToChurch,
      goToCampus = actions::navigateToCampuses,
      goToGallery = actions::navigateToGalleries,
      goToPrayer = actions::navigateToPrayer,
      goToDonations = actions::navigateToDonations,
      goToAdminLogin = actions::navigateToAdminLogin,
    )
  }
}