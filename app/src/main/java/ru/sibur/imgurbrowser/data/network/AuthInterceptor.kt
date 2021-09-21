package ru.sibur.imgurbrowser.data.network

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.sibur.imgurbrowser.domain.AuthChangeListener

class AuthInterceptor(
    private val clientId: String,
    private val authChangeListener: AuthChangeListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authData = runBlocking { authChangeListener.authDataState.firstOrNull() }
        val request = chain.request().newBuilder()
            .apply {
                if (authData != null) {
                    addHeader("Authorization", "Bearer ${authData.accessToken}")
                } else {
                    addHeader("Authorization", "Client-ID $clientId")
                }
            }
            .build()
        return chain.proceed(request)
    }
}
