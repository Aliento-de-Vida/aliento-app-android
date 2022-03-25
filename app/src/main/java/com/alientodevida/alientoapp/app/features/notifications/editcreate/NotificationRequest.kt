package com.alientodevida.alientoapp.app.features.notifications.editcreate

import com.alientodevida.alientoapp.domain.common.Image

data class NotificationRequest(
  val id: Int,
  val title: String,
  val content: String,
  val image: Image? = null,
  val date: String,
  val attachment: Attachment? = null,
) {
  val isNew: Boolean get() = id == 0
}