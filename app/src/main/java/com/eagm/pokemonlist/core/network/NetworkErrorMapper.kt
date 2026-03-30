package com.eagm.pokemonlist.core.network

import java.io.IOException
import java.net.SocketTimeoutException
import retrofit2.HttpException

fun Throwable.toNetworkError(): NetworkError {
    return when (this) {
        is SocketTimeoutException -> NetworkError.Timeout
        is IOException -> NetworkError.NoInternet
        is HttpException -> {
            when (code()) {
                404 -> NetworkError.NotFound
                in 500..599 -> NetworkError.ServerError
                else -> NetworkError.Unknown
            }
        }
        else -> NetworkError.Unknown
    }
}