package com.alientodevida.alientoapp.app.features.notifications.editcreate

import com.alientodevida.alientoapp.app.compose.components.attachment.AttachmentModel

data class NotificationRequest(
  val id: Int,
  val title: String,
  val content: String,
  val imageName: String,
  val date: String,
  val attachment: AttachmentModel? = null,
) {
  val isComplete: Boolean = title.isBlank().not() && content.isBlank().not() && imageName.isNotBlank()
  val isNew: Boolean get() = id == 0
}