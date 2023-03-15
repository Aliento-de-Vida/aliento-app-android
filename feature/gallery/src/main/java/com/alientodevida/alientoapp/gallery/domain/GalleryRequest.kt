package com.alientodevida.alientoapp.gallery.domain

import com.alientodevida.alientoapp.domain.common.Attachment

data class GalleryRequest(
    val id: Int,
    val name: String,

    val coverPicture: String,
    val attachment: Attachment? = null,

    val images: List<String>,
    val attachmentList: List<Attachment>,
)
