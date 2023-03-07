package com.alientodevida.alientoapp.ui.navigation

import androidx.navigation.NavHostController
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.notification.Notification

class MainActions(private val controller: NavHostController) {

    fun navigateToSermons() = controller.navigate(MainDestination.Sermons.path)
    fun navigateToChurch() = controller.navigate(MainDestination.Church.path)
    fun navigateToCampuses() = controller.navigate(MainDestination.Campuses.path)
    fun navigateToGalleries() = controller.navigate(MainDestination.Galleries.path)
    fun navigateToDonations() = controller.navigate(MainDestination.Donations.path)
    fun navigateToPrayer() = controller.navigate(MainDestination.Prayer.path)
    fun navigateToNotifications() = controller.navigate(MainDestination.Notifications.path)
    fun navigateToSettings() = controller.navigate(MainDestination.Settings.path)

    fun navigateToAdminLogin() = controller.navigate(MainDestination.AdminLogin.path)
    fun navigateToAdminHome(home: Home) = controller.navigate(MainDestination.homeAdmin(home))
    fun navigateToAdminCampus(campus: Campus?) =
        controller.navigate(MainDestination.campusAdmin(campus))

    fun navigateToAdminCampus() = controller.navigate(MainDestination.campusAdmin(null))
    fun navigateToAdminGallery(campus: Gallery?) =
        controller.navigate(MainDestination.galleryAdmin(campus))

    fun navigateToAdminGallery() = controller.navigate(MainDestination.galleryAdmin(null))
    fun navigateToAdminNotification(campus: Notification?) =
        controller.navigate(MainDestination.notificationAdmin(campus))

    fun navigateToAdminNotification() = controller.navigate(MainDestination.notificationAdmin(null))

}
