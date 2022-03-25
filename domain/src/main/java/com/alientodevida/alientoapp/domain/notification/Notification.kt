package com.alientodevida.alientoapp.domain.notification

import android.os.Parcelable
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.extensions.format
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.*

@Parcelize
@Serializable
data class Notification(
  val id: Int,
  val title: String,
  val content: String,
  val image: Image? = null,
  val date: String,
) : Parcelable {
  companion object {
    fun empty() = Notification(0, "", "", null, Date().format())
  }
}
