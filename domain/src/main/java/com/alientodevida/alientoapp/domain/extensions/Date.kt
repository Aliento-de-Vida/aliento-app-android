package com.alientodevida.alientoapp.domain.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.format(pattern: String): String =
  SimpleDateFormat(pattern).format(this)
