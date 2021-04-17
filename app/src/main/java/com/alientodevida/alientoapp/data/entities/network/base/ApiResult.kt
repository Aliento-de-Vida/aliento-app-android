package com.alientodevida.alientoapp.data.entities.network.base

import java.io.IOException

sealed class ApiResult<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : ApiResult<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U?, val code: Int) : ApiResult<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : ApiResult<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : ApiResult<Nothing, Nothing>()
}
