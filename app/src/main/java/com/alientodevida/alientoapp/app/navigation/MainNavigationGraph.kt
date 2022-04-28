package com.alientodevida.alientoapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

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

interface MobileDestination {
  val path: String
}

enum class MobileGraph(override val path: String) : MobileDestination {
  Main("main");
}

class GenericNavigationActions(private val navController: NavHostController) {
  fun navigateUp() = navController.navigateUp()
  fun back() = navController.popBackStack()
}