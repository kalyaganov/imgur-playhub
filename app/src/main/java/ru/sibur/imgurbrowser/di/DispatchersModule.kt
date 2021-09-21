package ru.sibur.imgurbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @DispatcherMain
    fun provideDispatcherMain(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO

    @Provides
    @DispatcherIO
    fun provideDispatcherDefault(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Default
}
