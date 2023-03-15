package com.alientodevida.alientoapp.notifications.data

import com.alientodevida.alientoapp.domain.common.Notification
import retrofit2.http.GET

interface NotificationApi {

    @GET("/v1/notifications")
    suspend fun getNotifications(): List<Notification>
}
