package com.alientodevida.alientoapp.domain.audio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Audio(
  @PrimaryKey
  val uri: String,
  val releaseDate: String?,
  val title: String,
  val subtitle: String,
  val duration: Int,
  val imageUrl: String
)
