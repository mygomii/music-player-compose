package com.mygomii.data.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.mygomii.data.servies.PlayController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providePlayService(exoPlayer: ExoPlayer) = PlayController(exoPlayer)

    @Singleton
    @Provides
    fun provideExoPlayer(context: Context): ExoPlayer = ExoPlayer.Builder(context).build()

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}