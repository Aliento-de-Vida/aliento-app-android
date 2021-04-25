package com.alientodevida.alientoapp.data.networking

import com.alientodevida.alientoapp.domain.entities.network.base.ApiResult
import com.alientodevida.alientoapp.domain.entities.network.base.ResponseError
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<ApiResult<S, E>> {

    override fun enqueue(callback: Callback<ApiResult<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        val result: ApiResult<S, E> = ApiResult.Success(body)
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(result)
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                ApiResult.Failure(ResponseError.UnknownResponseError(null))
                            )
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    when {
                        errorBody != null -> {
                            val aaa: ResponseError<E> = ResponseError.ApiResponseError(errorBody, code)
                            val result: ApiResult<S, E> = ApiResult.Failure(aaa)
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    result
                                )
                            )
                        }
                        // TODO: how to get error codes or parse error?
                        code != null -> {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    ApiResult.Failure(
                                    ResponseError.ApiResponseError(null, code)
                                ))
                            )
                        }
                        else -> {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    ApiResult.Failure(
                                    ResponseError.UnknownResponseError(null)
                                ))
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> ApiResult.Failure(
                        ResponseError.NetworkResponseError(throwable))
                    else -> ApiResult.Failure(ResponseError.UnknownResponseError(throwable))
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResult<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout() = Timeout().timeout(10, TimeUnit.SECONDS)
}
