package ru.sibur.imgurbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sibur.imgurbrowser.data.api.AccountApi
import ru.sibur.imgurbrowser.data.api.ImageApi
import ru.sibur.imgurbrowser.data.api.TagGalleryApi
import ru.sibur.imgurbrowser.data.api.TagsApi
import ru.sibur.imgurbrowser.data.errors.ImgurCallHandler
import ru.sibur.imgurbrowser.data.repository.AccountRepository
import ru.sibur.imgurbrowser.data.repository.AuthenticationRepository
import ru.sibur.imgurbrowser.data.repository.ImageRepository
import ru.sibur.imgurbrowser.data.repository.TagsRepository
import ru.sibur.imgurbrowser.data.room.dao.AuthDataDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTagsRepository(
        tagsApi: TagsApi,
        tagGalleryApi: TagGalleryApi,
        imgurCallHandler: ImgurCallHandler
    ): TagsRepository = TagsRepository(tagsApi, tagGalleryApi, imgurCallHandler)

    @Provides
    @Singleton
    fun provideImageRepository(
        imageApi: ImageApi,
        imgurCallHandler: ImgurCallHandler
    ): ImageRepository = ImageRepository(imageApi, imgurCallHandler)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authDataDao: AuthDataDao
    ): AuthenticationRepository = AuthenticationRepository(authDataDao)

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountApi: AccountApi,
        imgurCallHandler: ImgurCallHandler
    ): AccountRepository = AccountRepository(accountApi, imgurCallHandler)
}
