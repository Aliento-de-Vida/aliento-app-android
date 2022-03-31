package com.alientodevida.alientoapp.domain.file

import com.alientodevida.alientoapp.domain.notification.Attachment

interface FileRepository {
  suspend fun uploadImage(attachment: Attachment)
  suspend fun getAllImages(): List<String>
}