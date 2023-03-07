package com.alientodevida.alientoapp.ui.components.attachment

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.webkit.MimeTypeMap

fun Context.createAttachment(uri: Uri): AttachmentModel =
  AttachmentModel(uri, uri.toString().getExtensionFromPathOrNull() ?: getFileExtension(uri))

fun String.getExtensionFromPathOrNull(): String? {
  val indexOfDash = lastIndexOf('/')
  if (indexOfDash < 0) return null
  val filename = substring(indexOfDash + 1)
  return if (filename.contains(".")) filename.substring(filename.lastIndexOf(".") + 1) else null
}

fun Context.getFileExtension(uri: Uri): String? {
  val mimeTypeMap = MimeTypeMap.getSingleton()
  return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
}

fun Context.parcelFileDescriptor(
  uri: Uri,
  mode: String = "r",
): ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri, mode)
