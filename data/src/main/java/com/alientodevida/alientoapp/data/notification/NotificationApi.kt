package com.alientodevida.alientoapp.data.notification

import com.alientodevida.alientoapp.domain.notification.Notification
import retrofit2.http.GET

interface NotificationApi {
  
  @GET("/v1/notifications")
  suspend fun getNotifications(): List<Notification>
  
}