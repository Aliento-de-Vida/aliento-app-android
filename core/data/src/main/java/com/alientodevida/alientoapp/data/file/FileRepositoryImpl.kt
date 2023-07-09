package com.alientodevida.alientoapp.data.file

import android.content.Context
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.file.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import id.zelory.compressor.Compressor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private const val TAG = "FileRepositoryImpl"

class FileRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val fileApi: FileApi,
    private val logger: Logger,
) : FileRepository {

    override suspend fun uploadImage(attachment: Attachment) {
        val file = File(attachment.filePath).compressImage()
        val filePart = MultipartBody.Part.createFormData(
            file.name,
            attachment.name,
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull()),
        )
        fileApi.uploadImage(filePart = filePart)
    }

    private suspend fun File.compressImage(): File {
        logger.d("${this.name} size: ${this.length()} - original", TAG)
        val compressed = Compressor.compress(context, this)
        logger.d("${compressed.name} size: ${compressed.length()} - compressed", TAG)
        return compressed
    }

    override suspend fun getAllImages(): List<String> = fileApi.geAllImages()
}
