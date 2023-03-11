package com.alientodevida.alientoapp.admin.presentation.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alientodevida.alientoapp.admin.presentation.AdminHome
import com.alientodevida.alientoapp.admin.presentation.login.AdminLogin
import com.alientodevida.alientoapp.domain.common.Home
import com.alientodevida.alientoapp.ui.extensions.getParcelableValue
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.HOME_DESTINATION
import com.alientodevida.alientoapp.ui.navigation.MainDestination
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.adminLogin(
    genericActions: GenericNavigationActions,
) {
    composable(MainDestination.AdminLogin.path) {
        AdminLogin(
            viewModel = hiltViewModel(),
            onBackPressed = genericActions::back,
        )
    }
}

fun NavGraphBuilder.adminHome(
    genericActions: GenericNavigationActions,
) {
    composable(
        route = MainDestination.AdminHome.path,
        arguments = listOf(navArgument(HOME_DESTINATION) { type = HomeType() })
    ) {
        val home = it.arguments?.getParcelableValue(HOME_DESTINATION, Home::class.java)!!

        AdminHome(
            viewModel = hiltViewModel(),
            home = home,
            onBackPressed = genericActions::back,
        )
    }
}

class HomeType : NavType<Home>(
    isNullableAllowed = false
) {
    override fun put(bundle: Bundle, key: String, value: Home) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): Home =
        bundle.getParcelableValue(key, Home::class.java)

    override fun parseValue(value: String): Home {
        return Json.decodeFromString(value)
    }
}