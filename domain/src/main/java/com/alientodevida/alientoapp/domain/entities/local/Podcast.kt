package com.alientodevida.alientoapp.domain.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Podcast(
    @PrimaryKey
    val uri: String,
    val releaseDate: String,
    val name: String,
    val duration: Int,
    val imageUrl: String
)
