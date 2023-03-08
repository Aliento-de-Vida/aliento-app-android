package com.alientodevida.alientoapp.domain.video

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class  YoutubeVideo(
  var name: String,
  @PrimaryKey
  val id: String,
  val description: String,
  val date: String,
  val thumbnailsUrl: String?
) : Parcelable