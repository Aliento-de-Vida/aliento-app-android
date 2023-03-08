package com.alientodevida.alientoapp.gallery.presentation.editcreate

data class GalleryRequest(
    val id: Int,
    val name: String,

    val coverPicture: String,
    val attachment: com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel? = null,

    val images: List<String>,
    val attachmentList: List<com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel>,
) {
  val isComplete: Boolean =
    name.isBlank().not() &&
        coverPicture.isNotBlank() &&
        (images.isNotEmpty() || attachmentList.isNotEmpty())
  
  val isNew: Boolean get() = id == 0
}