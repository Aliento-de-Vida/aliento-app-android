package com.alientodevida.alientoapp.domain.notification

data class NotificationRequest(
  val id: Int,
  val title: String,
  val content: String,
  val imageName: String,
  val date: String,
  val attachment: Attachment? = null,
)