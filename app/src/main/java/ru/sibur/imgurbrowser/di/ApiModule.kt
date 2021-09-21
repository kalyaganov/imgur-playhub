package ru.sibur.imgurbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.sibur.imgurbrowser.data.api.AccountApi
import ru.sibur.imgurbrowser.data.api.ImageApi
import ru.sibur.imgurbrowser.data.api.TagGalleryApi
import ru.sibur.imgurbrowser.data.api.TagsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideTagsApi(retrofit: Retrofit): TagsApi {
        return retrofit.create(TagsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTagGalleryApi(retrofit: Retrofit): TagGalleryApi {
        return retrofit.create(TagGalleryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}
