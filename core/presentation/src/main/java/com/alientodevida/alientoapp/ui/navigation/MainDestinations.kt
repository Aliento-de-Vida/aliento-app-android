package com.alientodevida.alientoapp.ui.navigation

import android.net.Uri
import com.alientodevida.alientoapp.domain.common.Campus
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.domain.common.Notification
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.alientodevida.alientoapp.domain.home.Home as DomainHome

const val HOME_DESTINATION = "home"
const val GALLERY_DESTINATION = "gallery"
const val CAMPUS_DESTINATION = "campus"
const val NOTIFICATION_DESTINATION = "notification"

enum class MainDestination(override val path: String) : MobileDestination {
    Home("main/home"),
    Sermons("main/sermons"),
    Church("main/church"),
    Campuses("main/campus"),
    Galleries("main/gallery"),
    Donations("main/donations"),
    Prayer("main/prayer"),
    Notifications("main/notification/{$NOTIFICATION_DESTINATION}"),
    Settings("main/settings"),

    AdminLogin("main/admin/login"),
    AdminHome("main/admin/home/{$HOME_DESTINATION}"),
    AdminCampus("main/admin/campus/{$CAMPUS_DESTINATION}"),
    AdminNotifications("main/admin/notification/{$NOTIFICATION_DESTINATION}"),
    AdminGallery("main/admin/gallery/{$GALLERY_DESTINATION}");

    companion object {

        fun homeAdmin(home: DomainHome): String {
            val homeString = Uri.encode(Json.encodeToString(home))
            return "main/admin/home/$homeString"
        }

        fun campusAdmin(campus: Campus?): String {
            val campusString = Uri.encode(Json.encodeToString(campus ?: Campus.empty()))
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