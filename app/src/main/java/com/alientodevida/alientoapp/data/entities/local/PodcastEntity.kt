package com.alientodevida.alientoapp.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PodcastEntity(
    @PrimaryKey
    val uri: String,
    val releaseDate: String,
    val name: String,
    val duration: Int,
    val imageUrl: String
)
