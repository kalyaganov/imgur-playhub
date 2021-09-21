package ru.sibur.imgurbrowser.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.sibur.imgurbrowser.BuildConfig
import ru.sibur.imgurbrowser.data.errors.ImgurCallHandler
import ru.sibur.imgurbrowser.data.network.AuthInterceptor
import ru.sibur.imgurbrowser.domain.AuthChangeListener
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApiClientId apiClientId: String,
        authChangeListener: AuthChangeListener
    ): AuthInterceptor = AuthInterceptor(apiClientId, authChangeListener)

    @Provides
    @Singleton
    fun provideOkHttp(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
        )
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideCallExceptionHandler(): ImgurCallHandler = ImgurCallHandler()
}
