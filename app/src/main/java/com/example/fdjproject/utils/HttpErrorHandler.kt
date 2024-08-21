package com.example.fdjproject.utils

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> runSafeApiCall(
    apiCall: suspend () -> Result<T>
): Result<T> {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Result.failure(
                IOException(
                    throwable.message ?: "Unknown IO Exception"
                )
            )
            is HttpException -> {
                val code = throwable.code()
                val errorMessage =
                    "HttpException: code:$code, message: ${throwable.message ?: "Unknown HttpException Exception"}"
                Result.failure(Throwable(errorMessage))
            }
            else -> {
                Result.failure(throwable)
            }
        }
    }
}