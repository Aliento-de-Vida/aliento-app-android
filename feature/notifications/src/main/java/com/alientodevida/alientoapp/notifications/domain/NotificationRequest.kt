package com.alientodevida.alientoapp.notifications.domain

import com.alientodevida.alientoapp.domain.common.Attachment

data class NotificationRequest(
    val id: Int,
    val title: String,
    val content: String,
    val imageName: String,
    val date: String,
    val attachment: Attachment? = null,
)