package com.alientodevida.alientoapp.app.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.alientodevida.alientoapp.campus.list.Campuses
import com.alientodevida.alientoapp.app.features.church.Church
import com.alientodevida.alientoapp.app.features.donations.Donations
import com.alientodevida.alientoapp.gallery.list.Galleries
import com.alientodevida.alientoapp.app.features.home.Home
import com.alientodevida.alientoapp.app.features.notifications.list.Notifications
import com.alientodevida.alientoapp.app.features.prayer.Prayer
import com.alientodevida.alientoapp.sermons.Sermons
import com.alientodevida.alientoapp.settings.Settings

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

@Suppress("UNUSED_PARAMETER")
fun NavGraphBuilder.sermons( // TODO use same navigation design and move everything here
  navController: NavHostController,
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Sermons.path) {
    Sermons(
      onBackPressed = genericActions::back
    )
  }
}

fun NavGraphBuilder.church(
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Church.path) {
    Church(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
    )
  }
}

fun NavGraphBuilder.campuses(
  genericActions: GenericNavigationActions,
  actions: MainActions,
) {
  composable(MainDestination.Campuses.path) {
    Campuses(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
      goToEditCampus = actions::navigateToAdminCampus,
      goToCreateCampus = actions::navigateToAdminCampus,
    )
  }
}

fun NavGraphBuilder.galleries(
  genericActions: GenericNavigationActions,
  actions: MainActions,
) {
  composable(MainDestination.Galleries.path) {
    Galleries(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
      goToCreateGallery = actions::navigateToAdminGallery,
      goToEditGallery = actions::navigateToAdminGallery,
    )
  }
}

fun NavGraphBuilder.donations(
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Donations.path) {
    Donations(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
    )
  }
}

fun NavGraphBuilder.prayer(
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Prayer.path) {
    Prayer(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
    )
  }
}

fun NavGraphBuilder.notifications(
  genericActions: GenericNavigationActions,
  actions: MainActions,
) {
  composable(
    route = MainDestination.Notifications.path,
    deepLinks = listOf(navDeepLink { uriPattern = "https://todoserver-peter.herokuapp.com/{notification_id}" }),
  ) { backStackEntry ->
    val notificationId = backStackEntry.arguments?.getString("notification_id")
    Notifications(
      viewModel = hiltViewModel(),
      selectedNotificationId = notificationId,
      onBackPressed = genericActions::back,
      goToCreateNotification = actions::navigateToAdminNotification,
      goToEditNotification = actions::navigateToAdminNotification,
    )
  }
}

fun NavGraphBuilder.settings(
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Settings.path) {
    Settings(
      viewModel = hiltViewModel(),
      onBackPressed = genericActions::back,
    )
  }
}