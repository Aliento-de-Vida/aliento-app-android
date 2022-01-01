package com.alientodevida.alientoapp.domain.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageUrlEntity(
  val imageUrl: String,
  @PrimaryKey
  val searchUrl: String
)