package com.alientodevida.alientoapp.domain.entities.network.base

sealed class ApiResult<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : ApiResult<T, Nothing>()

    /**
     * Failure response
     */
    data class Failure<U : Any>(val responseError: ResponseError<U>) : ApiResult<Nothing, U>()
}
