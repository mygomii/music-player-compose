package com.mygomii.data.servies

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.mygomii.data.state.PlayerStates
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayController @Inject constructor(private val exoPlayer: ExoPlayer) : Player.Listener {
    private val _playerState = MutableStateFlow(PlayerStates.STATE_IDLE)
    val playerState: MutableStateFlow<PlayerStates> = _playerState

    val currentPlaybackPosition: Long
        get() = if (exoPlayer.currentPosition > 0) exoPlayer.currentPosition else 0L

    val currentTrackDuration: Long
        get() = if (exoPlayer.duration > 0) exoPlayer.duration else 0L


    fun initializePlayer(mediaItems: List<MediaItem>) {
        exoPlayer.addListener(this)
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    fun setUpTrack(index: Int, isTrackPlay: Boolean) {
        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.prepare()
        }

        exoPlayer.seekTo(index, 0)

        if (isTrackPlay) {
            exoPlayer.playWhenReady = true
        }
    }

    fun playPause() {
        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.prepare()
        }

        exoPlayer.playWhenReady = !exoPlayer.playWhenReady
    }

    fun releasePlayer() {
        exoPlayer.release()
    }

    fun seekToPosition(position: Long) {
        exoPlayer.seekTo(position)
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        _playerState.tryEmit(PlayerStates.STATE_ERROR)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        if (exoPlayer.playbackState == Player.STATE_READY) {
            if (playWhenReady) {
                _playerState.tryEmit(PlayerStates.STATE_PLAYING)
            } else {
                _playerState.tryEmit(PlayerStates.STATE_PAUSE)
            }
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
            _playerState.tryEmit(PlayerStates.STATE_NEXT_TRACK)
            _playerState.tryEmit(PlayerStates.STATE_PLAYING)
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_IDLE -> {
                _playerState.tryEmit(PlayerStates.STATE_IDLE)
            }

            Player.STATE_BUFFERING -> {
                _playerState.tryEmit(PlayerStates.STATE_BUFFERING)
            }

            Player.STATE_READY -> {
                _playerState.tryEmit(PlayerStates.STATE_READY)

                if (exoPlayer.playWhenReady) {
                    _playerState.tryEmit(PlayerStates.STATE_PLAYING)
                } else {
                    _playerState.tryEmit(PlayerStates.STATE_PAUSE)
                }
            }

            Player.STATE_ENDED -> {
                _playerState.tryEmit(PlayerStates.STATE_END)
            }
        }
    }
}