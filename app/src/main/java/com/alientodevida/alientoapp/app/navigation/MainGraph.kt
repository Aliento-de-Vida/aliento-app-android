package com.alientodevida.alientoapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.alientodevida.alientoapp.admin.presentation.navigation.adminHome
import com.alientodevida.alientoapp.admin.presentation.navigation.adminLogin
import com.alientodevida.alientoapp.campus.presentation.navigation.adminCampus
import com.alientodevida.alientoapp.campus.presentation.navigation.campuses
import com.alientodevida.alientoapp.church.navigation.church
import com.alientodevida.alientoapp.donations.navigation.donations
import com.alientodevida.alientoapp.gallery.presentation.navigation.adminGallery
import com.alientodevida.alientoapp.gallery.presentation.navigation.galleries
import com.alientodevida.alientoapp.home.presentation.navigation.home
import com.alientodevida.alientoapp.notifications.presentation.navigation.adminNotifications
import com.alientodevida.alientoapp.notifications.presentation.navigation.notifications
import com.alientodevida.alientoapp.prayer.navigation.prayer
import com.alientodevida.alientoapp.sermons.presentation.navigation.sermons
import com.alientodevida.alientoapp.settings.navigation.settings
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination
import com.alientodevida.alientoapp.ui.navigation.MobileGraph

@Composable
fun MainNavigationGraph() {
  val navController = rememberNavController()
  val genericActions = remember(navController) { GenericNavigationActions(navController) }
  val mainActions = remember(navController) { MainActions(navController) }

  NavHost(
    navController = navController,
    startDestination = MobileGraph.Main.path,
  ) {
    mainNavigation(
      navController = navController,
      genericActions = genericActions,
      mainActions = mainActions,
    )
  }
}

fun NavGraphBuilder.mainNavigation(
  navController: NavHostController,
  genericActions: GenericNavigationActions,
  mainActions: MainActions,
) {
  navigation(
    route = MobileGraph.Main.path,
    startDestination = MainDestination.Home.path,
  ) {
    home(mainActions)
    sermons(navController, genericActions)
    church(genericActions)
    campuses(genericActions, mainActions)
    galleries(genericActions, mainActions)
    donations(genericActions)
    prayer(genericActions)
    notifications(genericActions, mainActions)
    settings(genericActions)
    
    adminHome(genericActions)
    adminLogin(genericActions)
    adminCampus(genericActions)
    adminNotifications(genericActions)
    adminGallery(genericActions)
  }
}
