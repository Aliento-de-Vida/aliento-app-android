package com.alientodevida.alientoapp.app.logger

import com.alientodevida.alientoapp.app.BuildConfig
import com.alientodevida.alientoapp.domain.logger.Logger

class LoggerImpl() : Logger {
  
  companion object {
    private const val LOG_TAG = "Aliento App"
  }
  
  private var logcatEnabled = BuildConfig.DEBUG
  
  override fun v(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.v(logTag, msg, tr)
  }
  
  override fun d(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.d(logTag, msg, tr)
  }
  
  override fun i(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.i(logTag, msg, tr)
  }
  
  override fun w(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.w(logTag, msg, tr)
  }
  
  override fun e(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.e(logTag, msg, tr)
  }
  
  override fun wtf(msg: String, tag: String?, tr: Throwable?) {
    val logTag = tag ?: LOG_TAG
    if (logcatEnabled) android.util.Log.wtf(logTag, msg, tr)
  }
  
}