package com.mygomii.domain.usecases

import androidx.media3.common.MediaItem
import com.mygomii.data.servies.PlayController
import com.mygomii.data.state.PlayerStates
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class InitializePlayerUseCase @Inject constructor(private val playController: PlayController) {
    fun invoke(mediaItem: List<MediaItem>) = playController.initializePlayer(mediaItem)
}

class PlayAndPauseUseCase @Inject constructor(private val playController: PlayController) {
    fun invoke() = playController.playPause()
}

class ReleaseUseCase @Inject constructor(private val playController: PlayController) {
    fun invoke() = playController.releasePlayer()
}

class UpdateSeekToPosition @Inject constructor(private val playController: PlayController) {
    fun invoke(position: Long) = playController.seekToPosition(position)
}

class SetUpTrack @Inject constructor(private val playController: PlayController) {
    fun invoke(index: Int, isTrackPlay: Boolean) = playController.setUpTrack(index, isTrackPlay)
}

class PlayStateUseCase @Inject constructor(private val playController: PlayController) {
    fun invoke(): MutableStateFlow<PlayerStates> = playController.playerState
}

class GetCurrentPlaybackPosition @Inject constructor(private val playController: PlayController) {
    fun invoke(): Long = playController.currentPlaybackPosition
}

class GetCurrentTrackDuration @Inject constructor(private val playController: PlayController) {
    fun invoke(): Long = playController.currentTrackDuration
}