package com.alientodevida.alientoapp.common.logger

import com.alientodevida.alientoapp.common.BuildConfig

class Logger() {
  
  companion object {
    private const val LOG_TAG = "Aliento App"
  }
  
  private var logcatEnabled = BuildConfig.DEBUG
  
  fun v(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.v(logTag, msg, tr)
  }
  
  fun d(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.d(logTag, msg, tr)
  }
  
  fun i(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.i(logTag, msg, tr)
  }
  
  fun w(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.w(logTag, msg, tr)
  }
  
  fun e(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.e(logTag, msg, tr)
  }
  
  fun wtf(msg: String, tag: String? = null, tr: Throwable? = null) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.wtf(logTag, msg, tr)
  }
  
}