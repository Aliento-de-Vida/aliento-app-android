package com.alientodevida.alientoapp.prayer.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.prayer.Prayer
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

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
