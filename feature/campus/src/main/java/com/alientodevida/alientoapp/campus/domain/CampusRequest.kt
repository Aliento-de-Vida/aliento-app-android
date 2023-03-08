package com.alientodevida.alientoapp.campus.domain

import com.alientodevida.alientoapp.domain.common.Attachment
import com.alientodevida.alientoapp.domain.common.Location

data class CampusRequest(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val location: Location,
    val contact: String,

    val imageName: String,
    val attachment: Attachment? = null,

    val videoUrl: String?,

    val images: List<String>,
    val attachmentList: List<Attachment>,
)