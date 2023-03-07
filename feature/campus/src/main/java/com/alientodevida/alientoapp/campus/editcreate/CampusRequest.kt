package com.alientodevida.alientoapp.campus.editcreate

import com.alientodevida.alientoapp.ui.components.attachment.AttachmentModel
import com.alientodevida.alientoapp.domain.campus.Location

data class CampusRequest(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val location: Location,
    val contact: String,

    val imageName: String,
    val attachment: AttachmentModel? = null,

    val videoUrl: String?,

    val images: List<String>,
    val attachmentList: List<AttachmentModel>,
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