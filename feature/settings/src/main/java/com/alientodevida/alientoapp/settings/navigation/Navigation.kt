package com.alientodevida.alientoapp.settings.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.settings.Settings
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

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
