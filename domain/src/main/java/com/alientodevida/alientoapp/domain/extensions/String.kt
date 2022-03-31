package com.alientodevida.alientoapp.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String?.notEmptyOrNull(): String? = if (this == null || this.isEmpty()) null else this

fun String.toDate(pattern: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): Date? =
  SimpleDateFormat(pattern, Locale("es", "ES")).parse(this)

fun String.toImageUrl() = "https://todoserver-peter.herokuapp.com/v1/files${this}"

fun String.addTimeStamp(): String = "${this}_${System.currentTimeMillis()}"

fun String.removeExtension(): String {
  val lastIndex = if (indexOf(".") == -1) length else indexOf(".")
  return substring(0, lastIndex)
}