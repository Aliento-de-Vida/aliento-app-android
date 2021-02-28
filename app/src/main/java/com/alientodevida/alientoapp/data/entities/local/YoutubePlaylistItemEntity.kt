package com.alientodevida.alientoapp.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class YoutubePlaylistItemEntity(
        var name: String,
        @PrimaryKey
        val id: String,
        val description: String,
        val date: String,
        val thumbnilsUrl: String?
)