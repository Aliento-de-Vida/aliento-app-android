package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.home.Notification
import com.alientodevida.alientoapp.domain.home.NotificationRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HomeRepositoryImpl(
  private val api: HomeApi,
) : HomeRepository {
  override suspend fun getHome() = api.getHome()
  override suspend fun getNotifications() = api.getNotifications()
  override suspend fun deleteNotification(id: Int) = api.deleteNotification(id = id)
  
  override suspend fun editNotification(notification: NotificationRequest): Notification {
    val imageName = notification.image?.name!!
  
    uploadImage(notification, imageName)
    
    return api.editNotification(
      id = notification.id,
      title = notification.title,
      content = notification.content,
      image = imageName,
    )
  }
  
  
  override suspend fun createNotification(notification: NotificationRequest): Notification {
    val imageName = notification.image?.name!!
  
    uploadImage(notification, imageName)
  
    return api.createNotification(
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
      api.uploadImage(filePart = filePart)
    }
  }
  
}