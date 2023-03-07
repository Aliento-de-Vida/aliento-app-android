package com.alientodevida.alientoapp.campus.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alientodevida.alientoapp.campus.editcreate.EditCreateCampus
import com.alientodevida.alientoapp.campus.list.Campuses
import com.alientodevida.alientoapp.common.extensions.getParcelableValue
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.ui.navigation.CAMPUS_DESTINATION
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.adminCampus(
  genericActions: GenericNavigationActions,
) {
  composable(
    route = MainDestination.AdminCampus.path,
    arguments = listOf(navArgument(CAMPUS_DESTINATION) { type = CampusType() })
  ) {
    val campus = it.arguments?.getParcelableValue(CAMPUS_DESTINATION, Campus::class.java)!!

    EditCreateCampus(
      viewModel = hiltViewModel(),
      campus = campus,
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

class CampusType : NavType<Campus>(
  isNullableAllowed = true
) {
  override fun put(bundle: Bundle, key: String, value: Campus) {
    bundle.putParcelable(key, value)
  }

  override fun get(bundle: Bundle, key: String): Campus {
    return bundle.getParcelableValue(key, Campus::class.java)
  }

  override fun parseValue(value: String): Campus {
    return Json.decodeFromString(value)
  }
}
