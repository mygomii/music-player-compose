package com.mygomii.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mygomii.data.models.PlaybackState
import com.mygomii.data.models.Track
import com.mygomii.data.models.resetTracks
import com.mygomii.data.models.toMediaItemList
import com.mygomii.data.state.PlayerEvent
import com.mygomii.data.state.PlayerStates
import com.mygomii.domain.usecases.GetCurrentPlaybackPosition
import com.mygomii.domain.usecases.GetCurrentTrackDuration
import com.mygomii.domain.usecases.InitializePlayerUseCase
import com.mygomii.domain.usecases.PlayAndPauseUseCase
import com.mygomii.domain.usecases.PlayStateUseCase
import com.mygomii.domain.usecases.ReleaseUseCase
import com.mygomii.domain.usecases.SetUpTrack
import com.mygomii.domain.usecases.TrackListUseCase
import com.mygomii.domain.usecases.UpdateSeekToPosition
import com.mygomii.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val trackListUseCase: TrackListUseCase,
    private val initializePlayerUseCase: InitializePlayerUseCase,
    private val playAndPauseUseCase: PlayAndPauseUseCase,
    private val releaseUseCase: ReleaseUseCase,
    private val updateSeekToPosition: UpdateSeekToPosition,
    private val setUpTrack: SetUpTrack,
    private val playStateUseCase: PlayStateUseCase,
    private val getCurrentPlaybackPosition: GetCurrentPlaybackPosition,
    private val getCurrentTrackDuration: GetCurrentTrackDuration
) : BaseViewModel(), PlayerEvent {

    private val _tracks = mutableStateListOf<Track>()
    val tracks: List<Track> = _tracks

    private var isTrackPlay: Boolean = false

    var selectedTrack: Track? by mutableStateOf(null)
        private set

    private var selectedTrackIndex: Int by mutableIntStateOf(-1)

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> = _playbackState

    private var isAuto: Boolean = false

    init {
        _tracks.addAll(trackListUseCase.invoke())
        initializePlayerUseCase.invoke(tracks.toMediaItemList())
        observePlayerState()
    }


    private fun onTrackSelected(index: Int) {
        if (selectedTrackIndex == -1) {
            isTrackPlay = true
        }

        if (selectedTrackIndex == -1 || selectedTrackIndex != index) {
            _tracks.resetTracks()
            selectedTrackIndex = index
            setUpTrack()
        }
    }

    private fun setUpTrack() {
        if (!isAuto) {
            setUpTrack.invoke(selectedTrackIndex, isTrackPlay)
        }

        isAuto = false
    }

    private fun updateState(state: PlayerStates) {
        if (selectedTrackIndex != -1) {
            isTrackPlay = state == PlayerStates.STATE_PLAYING
            _tracks[selectedTrackIndex].state = state
            _tracks[selectedTrackIndex].isSelected = true
            selectedTrack = null
            selectedTrack = tracks[selectedTrackIndex]

            updatePlaybackState(state)

            if (state == PlayerStates.STATE_NEXT_TRACK) {
                isAuto = true
                onNextClick()
            }

            if (state == PlayerStates.STATE_END) {
                onTrackSelected(0)
            }
        }
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            playStateUseCase.invoke().collect {
                updateState(it)
            }
        }
    }

    private fun updatePlaybackState(state: PlayerStates) {
        playbackStateJob?.cancel()
        playbackStateJob = viewModelScope.launch {
            do {
                _playbackState.emit(
                    PlaybackState(
                        currentPlaybackPosition = getCurrentPlaybackPosition.invoke(),
                        currentTrackDuration = getCurrentTrackDuration.invoke()
                    )
                )
                delay(1000)
            } while (state == PlayerStates.STATE_PLAYING && isActive)
        }
    }


    override fun onPreviousClick() {
        if (selectedTrackIndex > 0) {
            onTrackSelected(selectedTrackIndex - 1)
        }
    }


    override fun onNextClick() {
        if (selectedTrackIndex < tracks.size - 1) {
            onTrackSelected(selectedTrackIndex + 1)
        }
    }

    override fun onPlayPauseClick() {
        playAndPauseUseCase.invoke()
    }

    override fun onTrackClick(track: Track) {
        onTrackSelected(tracks.indexOf(track))
    }


    override fun onSeekBarPositionChanged(position: Long) {
        viewModelScope.launch {
            updateSeekToPosition.invoke(position)

        }
    }

    override fun onCleared() {
        releaseUseCase.invoke()
        super.onCleared()

    }
}
