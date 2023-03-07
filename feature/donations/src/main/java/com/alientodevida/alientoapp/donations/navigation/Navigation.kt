package com.alientodevida.alientoapp.donations.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.donations.Donations
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

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
