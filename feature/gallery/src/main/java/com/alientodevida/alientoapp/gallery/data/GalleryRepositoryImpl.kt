package com.alientodevida.alientoapp.gallery.data

import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.domain.extensions.addTimeStamp
import com.alientodevida.alientoapp.domain.file.FileRepository

class GalleryRepositoryImpl(
    private val api: GalleryApi,
    private val adminApi: GalleryAdminApi,
    private val fileRepository: FileRepository,
) : com.alientodevida.alientoapp.gallery.domain.GalleryRepository {
  
  override suspend fun getGalleries() = api.getGalleries()
  override suspend fun deleteGallery(id: Int) = adminApi.deleteGallery(id)
  
  override suspend fun createGallery(gallery: com.alientodevida.alientoapp.gallery.domain.GalleryRequest): Gallery {
    val timestamp = "".addTimeStamp()
    val galleryCoverName = "gallery_cover$timestamp"
    fileRepository.uploadImage(Attachment(galleryCoverName, gallery.attachment!!.filePath))
  
    val imagesList = mutableListOf<String>()
    imagesList += gallery.images
  
    gallery.attachmentList.forEach {
      val imageName = "gallery_${timestamp}_gallery".addTimeStamp()
      fileRepository.uploadImage(Attachment(imageName, it.filePath))
      imagesList += imageName
    }
  
    return adminApi.createGallery(
      name = gallery.name,
      coverPicture = galleryCoverName,
      images = imagesList.joinToString(","),
    )
  }
  
  override suspend fun editGallery(gallery: com.alientodevida.alientoapp.gallery.domain.GalleryRequest): Gallery {
    val galleryCoverName = if (gallery.attachment != null) "gallery_cover".addTimeStamp() else gallery.coverPicture
    gallery.attachment?.let {
      fileRepository.uploadImage(Attachment(galleryCoverName, it.filePath))
    }

    val imagesList = mutableListOf<String>()
    imagesList += gallery.images
  
    gallery.attachmentList.forEach {
      val imageName = "gallery_${gallery.id}_gallery".addTimeStamp()
      fileRepository.uploadImage(Attachment(imageName, it.filePath))
      imagesList += imageName
    }
  
    return adminApi.editGallery(
      id = gallery.id,
      name = gallery.name,
      coverPicture = galleryCoverName,
      images = imagesList.joinToString(","),
    )
  }
  
}