package ru.sibur.imgurbrowser.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sibur.imgurbrowser.data.room.AppDatabase
import ru.sibur.imgurbrowser.data.room.dao.AuthDataDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "imgur-client-db").build()

    @Provides
    @Singleton
    fun provideAuthDataDao(appDatabase: AppDatabase): AuthDataDao = appDatabase.authDataDao()
}
