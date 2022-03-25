package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.net.Uri

data class Attachment(
  val uri: Uri,
  val fileExtension: String? = null,
) {
  val displayName: String
    get() {
      val toString = uri.toString()
      val name = toString.substring(toString.lastIndexOf('/') + 1)
      return fileExtension?.let { "$name.$it" } ?: name
    }
}
