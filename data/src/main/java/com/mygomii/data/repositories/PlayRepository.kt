package com.mygomii.data.repositories

import androidx.media3.common.MediaItem
import com.mygomii.data.state.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow

interface PlayRepository {
    suspend fun play(mediaItem: MediaItem)
    suspend fun playAndPause()
    suspend fun next()
    suspend fun previous()
    suspend fun state(): MutableStateFlow<PlayerState>
}
