package com.mygomii.data.repositories

import androidx.media3.common.MediaItem
import com.mygomii.data.servies.PlayController
import com.mygomii.data.state.PlayerEvent
import com.mygomii.data.state.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayRepositoryImpl @Inject constructor(
    private val playController: PlayController
) : PlayRepository {
    override suspend fun play(mediaItem: MediaItem) {
        playController.setMediaItems(mediaItem)
        playAndPause()

    }

    override suspend fun playAndPause() {
        playController.onEvent(PlayerEvent.CurrentMusic)
    }

    override suspend fun next() {

    }

    override suspend fun previous() {

    }

    override suspend fun state(): MutableStateFlow<PlayerState> {
        return playController.state
    }
}
