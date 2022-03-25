package com.alientodevida.alientoapp.data.notification

import com.alientodevida.alientoapp.domain.notification.Notification
import com.alientodevida.alientoapp.domain.notification.NotificationRepository
import com.alientodevida.alientoapp.domain.notification.NotificationRequest
import com.alientodevida.alientoapp.domain.preferences.Preferences
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class NotificationRepositoryImpl(
  private val api: NotificationApi,
  private val adminApi: NotificationAdminApi,
) : NotificationRepository {
  
  override suspend fun getNotifications() = api.getNotifications()
  
  override suspend fun deleteNotification(id: Int) = adminApi.deleteNotification(id)
  
  override suspend fun editNotification(notification: NotificationRequest): Notification {
    val imageName = notification.image?.name!!
  
    uploadImage(notification, imageName)
    
    return adminApi.editNotification(
      id = notification.id,
      title = notification.title,
      content = notification.content,
      image = imageName,
    )
  }
  
  override suspend fun createNotification(notification: NotificationRequest): Notification {
    val imageName = notification.image?.name!!
  
    uploadImage(notification, imageName)
  
    return adminApi.createNotification(
      title = notification.title,
      content = notification.content,
      image = imageName,
    )
  }

  private suspend fun uploadImage(
    notification: NotificationRequest,
    imageName: String
  ) {
    notification.attachment?.let {
      val file = File(it.filePath)
      val filePart = MultipartBody.Part.createFormData(
        imageName,
        file.name,
        file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
      )
      adminApi.uploadImage(filePart = filePart)
    }
  }
  
}