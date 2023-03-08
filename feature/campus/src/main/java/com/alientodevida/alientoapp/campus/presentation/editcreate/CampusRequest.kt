package com.alientodevida.alientoapp.campus.presentation.editcreate

import com.alientodevida.alientoapp.domain.common.Location

data class CampusRequest(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val location: Location,
    val contact: String,

    val imageName: String,
    val attachment: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel? = null,

    val videoUrl: String?,

    val images: List<String>,
    val attachmentList: List<com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel>,
) {
  val isComplete: Boolean =
    name.isBlank().not() &&
        description.isBlank().not() &&
        shortDescription.isNotBlank() &&
        imageName.isNotBlank() &&
        location.isValid &&
        contact.isNotBlank()
  
  val isNew: Boolean get() = id == 0
}