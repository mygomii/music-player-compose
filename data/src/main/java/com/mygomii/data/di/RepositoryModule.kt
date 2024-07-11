package com.mygomii.data.di

import com.mygomii.data.repositories.TrackRepository
import com.mygomii.data.repositories.TrackRepositoryImpl
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
    fun provideTrackRepository(): TrackRepository {
        return TrackRepositoryImpl()
    }

}
