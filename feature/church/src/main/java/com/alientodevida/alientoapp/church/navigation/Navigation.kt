package com.alientodevida.alientoapp.church.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.church.Church
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

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