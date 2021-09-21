package ru.sibur.imgurbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import ru.sibur.imgurbrowser.data.DefaultDispatchers
import ru.sibur.imgurbrowser.data.Dispatchers
import ru.sibur.imgurbrowser.domain.AuthChangeListener
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @ApiClientId
    fun provideApiClientId(): String = "c53072a740b85cf"

    @Provides
    @AuthUrl
    fun provideAuthUrl(@ApiClientId clientId: String): String =
        "https://api.imgur.com/oauth2/authorize?client_id=$clientId&response_type=token&state=APPLICATION_STATE"

    @Provides
    @Singleton
    fun provideDispatchers(): Dispatchers = DefaultDispatchers()

    @Provides
    @Singleton
    fun provideAuthChangeListener(@GlobalScope globalScope: CoroutineScope): AuthChangeListener =
        AuthChangeListener(globalScope)

    @Provides
    @Singleton
    @GlobalScope
    fun provideGlobalScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + kotlinx.coroutines.Dispatchers.Main)

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}
