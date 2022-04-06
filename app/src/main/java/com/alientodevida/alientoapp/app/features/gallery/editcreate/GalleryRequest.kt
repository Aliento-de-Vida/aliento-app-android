package com.alientodevida.alientoapp.app.features.gallery.editcreate

import com.alientodevida.alientoapp.app.compose.components.attachment.AttachmentModel

data class GalleryRequest(
  val id: Int,
  val name: String,
  
  val coverPicture: String,
  val attachment: AttachmentModel? = null,
  
  val images: List<String>,
  val attachmentList: List<AttachmentModel>,
) {
  val isComplete: Boolean =
    name.isBlank().not() &&
        coverPicture.isNotBlank() &&
        (images.isNotEmpty() || attachmentList.isNotEmpty())
  
  val isNew: Boolean get() = id == 0
}