package com.mygomii.data.di

import com.mygomii.data.repositories.MusicRepository
import com.mygomii.data.repositories.MusicRepositoryImpl
import com.mygomii.data.repositories.PlayRepository
import com.mygomii.data.repositories.PlayRepositoryImpl
import com.mygomii.data.servies.PlayController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlayRepository(playController: PlayController): PlayRepository {
        return PlayRepositoryImpl(playController)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(): MusicRepository {
        return MusicRepositoryImpl()
    }
}
