package com.alientodevida.alientoapp.notifications.domain

import com.alientodevida.alientoapp.domain.common.Notification

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>
    suspend fun deleteNotification(id: Int)
    suspend fun editNotification(notification: NotificationRequest): Notification
    suspend fun createNotification(notification: NotificationRequest): Notification
}
