package com.alientodevida.alientoapp.domain.gallery

import com.alientodevida.alientoapp.domain.common.Attachment

data class GalleryRequest(
  val id: Int,
  val name: String,
  
  val coverPicture: String,
  val attachment: Attachment? = null,
  
  val images: List<String>,
  val attachmentList: List<Attachment>,
)