package com.alientodevida.alientoapp.campus.data

import com.alientodevida.alientoapp.campus.domain.CampusRepository
import com.alientodevida.alientoapp.campus.domain.CampusRequest
import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.common.Campus
import com.alientodevida.alientoapp.domain.extensions.addTimeStamp
import com.alientodevida.alientoapp.domain.file.FileRepository

class CampusRepositoryImpl(
    private val campusApi: CampusApi,
    private val campusAdminApi: CampusAdminApi,
    private val fileRepository: FileRepository,
) : CampusRepository {
    override suspend fun getCampus() = campusApi.getCampus()

    override suspend fun deleteNotification(id: Int) = campusAdminApi.deleteCampus(id)

    override suspend fun createCampus(campusRequest: CampusRequest): Campus {
        val timestamp = "".addTimeStamp()
        val campusCoverName = "campus_cover$timestamp"
        fileRepository.uploadImage(Attachment(campusCoverName, campusRequest.attachment!!.filePath))

        val imagesList = mutableListOf<String>()
        imagesList += campusRequest.images

        campusRequest.attachmentList.forEach {
            val imageName = "campus_${timestamp}_gallery".addTimeStamp()
            fileRepository.uploadImage(Attachment(imageName, it.filePath))
            imagesList += imageName
        }

        return campusAdminApi.createCampus(
            name = campusRequest.name,
            description = campusRequest.description,
            shortDescription = campusRequest.shortDescription,
            image_url = campusCoverName,
            video_url = campusRequest.videoUrl ?: "",
            latitude = campusRequest.location.latitude,
            longitude = campusRequest.location.longitude,
            contact = campusRequest.location.longitude,
            images = imagesList.joinToString(","),
        )
    }

    override suspend fun editCampus(campusRequest: CampusRequest): Campus {
        val campusCoverName =
            if (campusRequest.attachment != null) "campus_cover".addTimeStamp() else campusRequest.imageName
        campusRequest.attachment?.let {
            fileRepository.uploadImage(Attachment(campusCoverName, it.filePath))
        }

        val imagesList = mutableListOf<String>()
        imagesList += campusRequest.images

        campusRequest.attachmentList.forEach {
            val imageName = "campus_${campusRequest.id}_gallery".addTimeStamp()
            fileRepository.uploadImage(Attachment(imageName, it.filePath))
            imagesList += imageName
        }

        return campusAdminApi.editCampus(
            id = campusRequest.id,
            name = campusRequest.name,
            description = campusRequest.description,
            shortDescription = campusRequest.shortDescription,
            image_url = campusCoverName,
            video_url = campusRequest.videoUrl ?: "",
            latitude = campusRequest.location.latitude,
            longitude = campusRequest.location.longitude,
            contact = campusRequest.location.longitude,
            images = imagesList.joinToString(","),
        )
    }
}