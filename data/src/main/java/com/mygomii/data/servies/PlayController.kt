package com.mygomii.data.servies

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.mygomii.data.state.PlayerEvent
import com.mygomii.data.state.PlayerState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class PlayController @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {
    private val _playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState.Initial)
    val state: MutableStateFlow<PlayerState> = _playerState

    private var progressJob: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun setMediaItems(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {
                _playerState.value = PlayerState.Buffering(progress = exoPlayer.currentPosition)
            }

            ExoPlayer.STATE_READY -> {
                _playerState.value = PlayerState.Ready(duration = exoPlayer.duration)
            }

            Player.STATE_ENDED -> {
//                TODO()
            }

            Player.STATE_IDLE -> {
//                TODO()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _playerState.value = PlayerState.Playing(isPlaying = isPlaying)

        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }

        } else {
            stopProgressUpdate()
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        when (reason) {
            Player.MEDIA_ITEM_TRANSITION_REASON_AUTO -> {
                exoPlayer.playWhenReady = true
            }

            else -> super.onMediaItemTransition(mediaItem, reason)
        }
    }

    private suspend fun startProgressUpdate() = progressJob.run {
        while (true) {
            //delay(5000)
            delay(1.seconds / 30)
            //println("exoPlayer.currentPosition " + exoPlayer.currentPosition)
            _playerState.value = PlayerState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        progressJob?.cancel()
        _playerState.value = PlayerState.Playing(isPlaying = false)
    }

    override fun onPlayerError(error: PlaybackException) {
        val cause = error.cause
        System.out.println(cause?.message)
    }

    suspend fun onEvent(event: PlayerEvent) {
        when (event) {
            PlayerEvent.Backward -> {
                println("exoPlayer.seekBackIncrement " + exoPlayer.seekBackIncrement)
                exoPlayer.seekTo((if (exoPlayer.currentPosition - 10 * 1000f < 0) 0f else exoPlayer.currentPosition - 10 * 1000f).toLong())
            }

            PlayerEvent.Forward -> {
                exoPlayer.seekTo((exoPlayer.currentPosition + 10 * 1000f).toLong())
            }

            PlayerEvent.PlayPause -> {
                playPause()
            }

            PlayerEvent.Stop -> {
                exoPlayer.stop()
                _playerState.value = PlayerState.Playing(isPlaying = false)
                stopProgressUpdate()
            }

            is PlayerEvent.UpdateProgress -> {
                exoPlayer.seekTo((exoPlayer.duration * event.updatedProgress).toLong())
            }

            is PlayerEvent.SeekTo -> {
                exoPlayer.seekTo(event.seekPos)
            }

            is PlayerEvent.CurrentMusic -> {
                playPause()

//                val count = exoPlayer.mediaItemCount
//                playPause()
//                for (i in 0 until count) {
//                    val mediaItem = exoPlayer.getMediaItemAt(i)
//                    if (mediaItem.mediaId.toInt() == event.mediaId) {
//                        if (exoPlayer.currentMediaItemIndex == i) {
//                            playPause()
//                        } else {
//                            println("event.selectedMediaIdx " + event.selectedMediaIdx)
//                            println("event.selectedMediaIdx pos i " + i)
//                            exoPlayer.seekToDefaultPosition(i)
//                            _state.value = PlayerState.Playing(isPlaying = true)
//                            exoPlayer.playWhenReady = true
//                            startProgressUpdate()
//                            break;
//                        }
//                    }
//                }
            }

            PlayerEvent.SkipToPrevious -> {
                val prevIdx = exoPlayer.currentMediaItemIndex - 1
                if (prevIdx >= 0) {
                    exoPlayer.seekToDefaultPosition(prevIdx)
                    _playerState.value = PlayerState.Playing(isPlaying = true)
                    exoPlayer.playWhenReady = true
                    startProgressUpdate()
                }
            }

            PlayerEvent.SkipToNext -> {
                val nextIdx = exoPlayer.currentMediaItemIndex + 1
                if (nextIdx >= 0 && nextIdx < exoPlayer.mediaItemCount) {
                    exoPlayer.seekToDefaultPosition(nextIdx)
                    _playerState.value = PlayerState.Playing(isPlaying = true)
                    exoPlayer.playWhenReady = true
                    startProgressUpdate()
                }
            }
        }
    }

    private suspend fun playPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _playerState.value = PlayerState.Playing(isPlaying = true)
            startProgressUpdate()
        }
    }


}