package com.alientodevida.alientoapp.domain.file

import com.alientodevida.alientoapp.domain.common.Attachment

interface FileRepository {
  suspend fun uploadImage(attachment: com.alientodevida.alientoapp.domain.common.Attachment)
  suspend fun getAllImages(): List<String>
}