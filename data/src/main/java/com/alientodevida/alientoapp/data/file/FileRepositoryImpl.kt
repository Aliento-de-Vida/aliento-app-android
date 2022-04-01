package com.alientodevida.alientoapp.data.file

import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.domain.common.Attachment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileRepositoryImpl(
  private val fileApi: FileApi,
) : FileRepository {
  
  override suspend fun uploadImage(attachment: Attachment) {
    val file = File(attachment.filePath)
    val filePart = MultipartBody.Part.createFormData(
      file.name,
      attachment.name,
      file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    )
    fileApi.uploadImage(filePart = filePart)
  }
  
  override suspend fun getAllImages(): List<String> = fileApi.geAllImages()
  
}