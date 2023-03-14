package com.alientodevida.alientoapp.notifications.presentation.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.alientodevida.alientoapp.domain.common.Notification
import com.alientodevida.alientoapp.notifications.presentation.editcreate.EditNotification
import com.alientodevida.alientoapp.notifications.presentation.list.Notifications
import com.alientodevida.alientoapp.ui.extensions.getParcelableValue
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination
import com.alientodevida.alientoapp.ui.navigation.NOTIFICATION_DESTINATION
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.adminNotifications(
    genericActions: GenericNavigationActions,
) {
    composable(
        route = MainDestination.AdminNotifications.path,
        arguments = listOf(navArgument(NOTIFICATION_DESTINATION) { type = NotificationType() })
    ) {
        val notification =
            it.arguments?.getParcelableValue(NOTIFICATION_DESTINATION, Notification::class.java)!!

        EditNotification(
            viewModel = hiltViewModel(),
            notification = notification,
            onBackPressed = genericActions::back,
        )
    }
}

fun NavGraphBuilder.notifications(
    genericActions: GenericNavigationActions,
    actions: MainActions,
) {
    composable(
        route = MainDestination.Notifications.path,
        deepLinks = listOf(navDeepLink {
            uriPattern = "https://todoserver-peter.herokuapp.com/{notification_id}"
        }),
    ) { backStackEntry ->
        val notificationId = backStackEntry.arguments?.getString("notification_id")
        Notifications(
            viewModel = hiltViewModel(),
            selectedNotificationId = notificationId,
            onBackPressed = genericActions::back,
            goToCreateNotification = actions::navigateToAdminNotification,
            goToEditNotification = actions::navigateToAdminNotification,
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