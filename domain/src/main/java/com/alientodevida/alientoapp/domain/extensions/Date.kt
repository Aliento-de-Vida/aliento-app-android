package com.alientodevida.alientoapp.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String): String =
  SimpleDateFormat(pattern, Locale("es", "ES")).format(this)
