package com.alientodevida.alientoapp.domain.logger

interface Logger {
	fun v(msg: String, tag: String? = null, tr: Throwable? = null)
	fun d(msg: String, tag: String? = null, tr: Throwable? = null)
	fun i(msg: String, tag: String? = null, tr: Throwable? = null)
	fun w(msg: String, tag: String? = null, tr: Throwable? = null)
	fun e(msg: String, tag: String? = null, tr: Throwable? = null)
	fun wtf(msg: String, tag: String? = null, tr: Throwable? = null)
}
