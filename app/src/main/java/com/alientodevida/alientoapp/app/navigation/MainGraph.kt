package com.alientodevida.alientoapp.app.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.notification.Notification
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.alientodevida.alientoapp.domain.campus.Campus as DomainCampus
import com.alientodevida.alientoapp.domain.home.Home as DomainHome

enum class MainDestination(override val path: String) : MobileDestination {
  Home("main/home"),
  Sermons("main/sermons"),
  Church("main/church"),
  Campuses("main/campus"),
  Galleries("main/gallery"),
  Donations("main/donations"),
  Prayer("main/prayer"),
  Notifications("main/notification"),
  Settings("main/settings"),
  
  AdminLogin("main/admin/login"),
  AdminHome("main/admin/home/{${MainDestination.HOME}}"),
  AdminCampus("main/admin/campus/{${MainDestination.CAMPUS}}"),
  AdminNotifications("main/admin/notification/{${MainDestination.NOTIFICATION}}"),
  AdminGallery("main/admin/gallery/{${MainDestination.GALLERY}}");
  
  companion object {
    const val HOME = "home"
    const val GALLERY = "gallery"
    const val CAMPUS = "campus"
    const val NOTIFICATION = "notification"
    
    fun homeAdmin(home: DomainHome): String {
      val homeString = Uri.encode(Json.encodeToString(home))
      return "main/admin/home/$homeString"
    }
    
    fun campusAdmin(campus: DomainCampus?): String {
      val campusString = Uri.encode(Json.encodeToString(campus ?: DomainCampus.empty()))
      return "main/admin/campus/$campusString"
    }
    
    fun galleryAdmin(gallery: Gallery?): String {
      val galleryString = Uri.encode(Json.encodeToString(gallery ?: Gallery.empty()))
      return "main/admin/gallery/$galleryString"
    }
    
    fun notificationAdmin(notification: Notification?): String {
      val galleryString = Uri.encode(Json.encodeToString(notification ?: Notification.empty()))
      return "main/admin/notification/$galleryString"
    }
  }
}

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
  fun navigateToAdminHome(home: DomainHome) = controller.navigate(MainDestination.homeAdmin(home))
  fun navigateToAdminCampus(campus: DomainCampus?) =
    controller.navigate(MainDestination.campusAdmin(campus))
  
  fun navigateToAdminCampus() = controller.navigate(MainDestination.campusAdmin(null))
  fun navigateToAdminGallery(campus: Gallery?) =
    controller.navigate(MainDestination.galleryAdmin(campus))
  
  fun navigateToAdminGallery() = controller.navigate(MainDestination.galleryAdmin(null))
  fun navigateToAdminNotification(campus: Notification?) =
    controller.navigate(MainDestination.notificationAdmin(campus))
  
  fun navigateToAdminNotification() = controller.navigate(MainDestination.notificationAdmin(null))
  
}

fun NavGraphBuilder.mainNavigation(
  navController: NavHostController,
  genericActions: GenericNavigationActions,
  mainActions: MainActions,
) {
  navigation(
    route = MobileGraph.Main.path,
    startDestination = MainDestination.Home.path,
  ) {
    home(mainActions)
    sermons(navController, genericActions)
    church(genericActions)
    campuses(genericActions, mainActions)
    galleries(genericActions, mainActions)
    donations(genericActions)
    prayer(genericActions)
    notifications(genericActions, mainActions)
    settings(genericActions)
    
    adminHome(genericActions)
    adminLogin(genericActions)
    adminCampus(genericActions)
    adminNotifications(genericActions)
    adminGallery(genericActions)
  }
}
