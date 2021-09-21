package ru.sibur.imgurbrowser.data.errors

import java.lang.Exception

sealed class ApiException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NoInternetException(cause: Throwable) : ApiException("Connection issues", cause)
    class ResponseCodeException(val code: Int) : ApiException("Response call exception")
    class UnknownException(cause: Throwable) : ApiException("Unknown exception", cause)
}
