package com.alientodevida.alientoapp.domain.file

interface FileRepository {
    suspend fun uploadImage(attachment: com.alientodevida.alientoapp.domain.common.Attachment)
    suspend fun getAllImages(): List<String>
}
