package com.alientodevida.alientoapp.app.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alientodevida.alientoapp.app.features.admin.login.AdminLogin
import com.alientodevida.alientoapp.campus.editcreate.EditCreateCampus
import com.alientodevida.alientoapp.app.features.home.admin.AdminHome
import com.alientodevida.alientoapp.app.features.notifications.editcreate.EditNotification
import com.alientodevida.alientoapp.common.extensions.getParcelableValue
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.notification.Notification
import com.alientodevida.alientoapp.gallery.editcreate.EditCreateGallery
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

fun NavGraphBuilder.adminNotifications(
    genericActions: GenericNavigationActions,
) {
    composable(
        route = MainDestination.AdminNotifications.path,
        arguments = listOf(navArgument(NOTIFICATION_DESTINATION) { type = NotificationType() })
    ) {
        val notification = it.arguments?.getParcelableValue(NOTIFICATION_DESTINATION, Notification::class.java)!!

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
        return bundle.getParcelableValue(key, Notification::class.java)
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
        arguments = listOf(navArgument(GALLERY_DESTINATION) { type = GalleryType() })
    ) {
        val gallery = it.arguments?.getParcelableValue(GALLERY_DESTINATION, Gallery::class.java)!!

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
        return bundle.getParcelableValue(key, Gallery::class.java)
    }

    override fun parseValue(value: String): Gallery {
        return Json.decodeFromString(value)
    }
}