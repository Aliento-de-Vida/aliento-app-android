package com.alientodevida.alientoapp.data.entities.network.base

import java.io.IOException

sealed class ResponseError<out F : Any> {
    /**
     * Failure response with body
     */
    data class ApiResponseError<F : Any>(val body: F?, val code: Int) : ResponseError<F>()

    /**
     * Network error
     */
    data class NetworkResponseError(val error: IOException) : ResponseError<Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownResponseError(val error: Throwable?) : ResponseError<Nothing>()
}