package ru.sibur.imgurbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import ru.sibur.imgurbrowser.data.Dispatchers
import ru.sibur.imgurbrowser.data.repository.AuthenticationRepository
import ru.sibur.imgurbrowser.data.repository.ImageRepository
import ru.sibur.imgurbrowser.data.repository.TagsRepository
import ru.sibur.imgurbrowser.domain.AuthChangeListener
import ru.sibur.imgurbrowser.domain.AuthenticationInteractor
import ru.sibur.imgurbrowser.domain.ImageInteractor
import ru.sibur.imgurbrowser.domain.TagsInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {

    @Provides
    @Singleton
    fun provideTagsInteractor(
        tagsRepository: TagsRepository,
        dispatchers: Dispatchers
    ): TagsInteractor =
        TagsInteractor(tagsRepository, dispatchers)

    @Provides
    @Singleton
    fun provideImageInteractor(
        imageRepository: ImageRepository,
        dispatchers: Dispatchers
    ): ImageInteractor =
        ImageInteractor(imageRepository, dispatchers)

    @Provides
    @Singleton
    fun provideAuthenticationInteractor(
        authenticationRepository: AuthenticationRepository,
        authChangeListener: AuthChangeListener,
        dispatchers: Dispatchers,
        @GlobalScope globalScope: CoroutineScope
    ): AuthenticationInteractor =
        AuthenticationInteractor(
            authenticationRepository,
            authChangeListener,
            globalScope,
            dispatchers.io
        )
}
