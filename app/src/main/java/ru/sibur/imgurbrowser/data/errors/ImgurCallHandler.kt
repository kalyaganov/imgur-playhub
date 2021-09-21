package ru.sibur.imgurbrowser.data.errors

import retrofit2.Call
import java.net.SocketTimeoutException

class ImgurCallHandler {
    fun <T, F> handleCallOrThrow(call: Call<T>, mapper: T.() -> F): F {
        try {
            val response = call.execute()
            if (!response.isSuccessful) {
                throw ApiException.ResponseCodeException(response.code())
            }
            return response.body()!!.mapper()
        } catch (t: Throwable) {
            if (t is SocketTimeoutException) {
                throw ApiException.NoInternetException(t)
            }
            throw ApiException.UnknownException(t)
        }
    }
}
