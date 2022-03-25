package com.alientodevida.alientoapp.domain.home

interface HomeRepository {
  suspend fun getHome(): Home
  suspend fun getNotifications(): List<Notification>
  suspend fun deleteNotification(id: Int)
  suspend fun editNotification(notification: NotificationRequest): Notification
  suspend fun createNotification(notification: NotificationRequest): Notification
}