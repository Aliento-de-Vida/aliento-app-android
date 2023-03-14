package com.alientodevida.alientoapp.notifications.presentation.editcreate

data class NotificationRequest(
    val id: Int,
    val title: String,
    val content: String,
    val imageName: String,
    val date: String,
    val attachment: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel? = null,
) {
    val isComplete: Boolean =
        title.isBlank().not() && content.isBlank().not() && imageName.isNotBlank()
    val isNew: Boolean get() = id == 0
}