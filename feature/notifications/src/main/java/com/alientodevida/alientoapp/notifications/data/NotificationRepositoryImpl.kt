package com.alientodevida.alientoapp.notifications.data

import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.common.Notification
import com.alientodevida.alientoapp.domain.extensions.addTimeStamp
import com.alientodevida.alientoapp.domain.file.FileRepository

class NotificationRepositoryImpl(
    private val api: NotificationApi,
    private val adminApi: NotificationAdminApi,
    private val fileRepository: FileRepository,
) : com.alientodevida.alientoapp.notifications.domain.NotificationRepository {

    override suspend fun getNotifications() = api.getNotifications()

    override suspend fun deleteNotification(id: Int) = adminApi.deleteNotification(id)

    override suspend fun editNotification(
        notification: com.alientodevida.alientoapp.notifications.domain.NotificationRequest,
    ): Notification {
        val imageName =
            if (notification.attachment != null) "notification".addTimeStamp() else notification.imageName
        notification.attachment?.let {
            fileRepository.uploadImage(Attachment(imageName, it.filePath))
        }

        return adminApi.editNotification(
            id = notification.id,
            title = notification.title,
            content = notification.content,
            image = imageName,
        )
    }

    override suspend fun createNotification(
        notification: com.alientodevida.alientoapp.notifications.domain.NotificationRequest,
    ): Notification {
        val imageName =
            if (notification.attachment != null) "notification".addTimeStamp() else notification.imageName
        notification.attachment?.let {
            fileRepository.uploadImage(Attachment(imageName, it.filePath))
        }

        return adminApi.createNotification(
            title = notification.title,
            content = notification.content,
            image = imageName,
        )
    }
}
