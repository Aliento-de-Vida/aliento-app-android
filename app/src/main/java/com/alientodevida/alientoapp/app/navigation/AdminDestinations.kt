package com.alientodevida.alientoapp.app.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alientodevida.alientoapp.app.features.admin.login.AdminLogin
import com.alientodevida.alientoapp.app.features.campus.editcreate.EditCreateCampus
import com.alientodevida.alientoapp.app.features.gallery.editcreate.EditCreateGallery
import com.alientodevida.alientoapp.app.features.home.admin.AdminHome
import com.alientodevida.alientoapp.app.features.notifications.editcreate.EditNotification
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.notification.Notification
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
    arguments = listOf(navArgument(MainDestination.HOME) { type = HomeType() })
  ) {
    val home = it.arguments?.getParcelable<Home>(MainDestination.HOME)!!
  
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
  
  override fun get(bundle: Bundle, key: String): Home {
    return (bundle.getParcelable<Home>(key) as Home)
  }
  
  override fun parseValue(value: String): Home {
    return Json.decodeFromString(value)
  }
}

fun NavGraphBuilder.adminCampus(
  genericActions: GenericNavigationActions,
) {
  composable(
    route = MainDestination.AdminCampus.path,
    arguments = listOf(navArgument(MainDestination.CAMPUS) { type = CampusType() })
  ) {
    val campus = it.arguments?.getParcelable<Campus>(MainDestination.CAMPUS)!!
  
    EditCreateCampus(
      viewModel = hiltViewModel(),
      campus = campus,
      onBackPressed = genericActions::back,
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
    return (bundle.getParcelable<Campus>(key) as Campus)
  }
  
  override fun parseValue(value: String): Campus {
    return Json.decodeFromString(value)
  }
}

fun NavGraphBuilder.adminNotifications(
  genericActions: GenericNavigationActions,
) {
  composable(
    route = MainDestination.AdminNotifications.path,
    arguments = listOf(navArgument(MainDestination.NOTIFICATION) { type = NotificationType() })
  ) {
    val notification = it.arguments?.getParcelable<Notification>(MainDestination.NOTIFICATION)!!
  
    EditNotification(
      viewModel = hiltViewModel(),
      notification = notification,
      onBackPressed = genericActions::back,
    )
  }
}

class NotificationType : NavType<Notification>(
  isNullableAllowed = true
) {
  override fun put(bundle: Bundle, key: String, value: Notification) {
    bundle.putParcelable(key, value)
  }
  
  override fun get(bundle: Bundle, key: String): Notification {
    return (bundle.getParcelable<Notification>(key) as Notification)
  }
  
  override fun parseValue(value: String): Notification {
    return Json.decodeFromString(value)
  }
}

fun NavGraphBuilder.adminGallery(
  genericActions: GenericNavigationActions,
) {
  composable(
    route = MainDestination.AdminGallery.path,
    arguments = listOf(navArgument(MainDestination.GALLERY) { type = GalleryType() })
  ) {
    val gallery = it.arguments?.getParcelable<Gallery>(MainDestination.GALLERY)!!

    EditCreateGallery(
      viewModel = hiltViewModel(),
      gallery = gallery,
      onBackPressed = genericActions::back,
    )
  }
}

class GalleryType : NavType<Gallery>(
  isNullableAllowed = true
) {
  override fun put(bundle: Bundle, key: String, value: Gallery) {
    bundle.putParcelable(key, value)
  }
  
  override fun get(bundle: Bundle, key: String): Gallery {
    return (bundle.getParcelable<Gallery>(key) as Gallery)
  }
  
  override fun parseValue(value: String): Gallery {
    return Json.decodeFromString(value)
  }
}