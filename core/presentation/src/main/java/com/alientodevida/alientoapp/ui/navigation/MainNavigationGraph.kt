package com.alientodevida.alientoapp.ui.navigation

import androidx.navigation.NavHostController

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