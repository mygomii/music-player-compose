package com.mygomii.presentation.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mygomii.data.models.Music
import com.mygomii.data.state.PlayerState
import com.mygomii.domain.state.HomeState
import com.mygomii.domain.state.HomeUiState
import com.mygomii.domain.state.UiState
import com.mygomii.domain.usecases.MusicUseCase
import com.mygomii.domain.usecases.PauseUseCase
import com.mygomii.domain.usecases.PlayUseCase
import com.mygomii.domain.usecases.PlayerStateUseCase
import com.mygomii.presentation.screen.base.BaseViewModel
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicUseCase: MusicUseCase,
    private val playUseCase: PlayUseCase,
    private val playerStateUseCase: PlayerStateUseCase,
    private val pauseUseCase: PauseUseCase,
) : BaseViewModel() {
    private val _screenStateStream = MutableStateFlow<UiState>(UiState.Loading)
    val screenStateStream: StateFlow<UiState> = _screenStateStream

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val musics = musicUseCase.musics()

            if (musics.isEmpty()) {
                _screenStateStream.value = UiState.Failure("")
            } else {
                _screenStateStream.value = UiState.Success(musics)
            }
        }
    }

    init {
        viewModelScope.launch {
            playerStateUseCase.invoke().collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> {
                        val newProgress = playerState.progress
                        _homeState.value = homeState.value.copy(
                            progress = newProgress.toFloat(),
                        )
                    }

                    is PlayerState.CurrentlyPlaying -> {
                        println("event listener currently playing idx " + playerState.mediaItemIdx)
                        _homeState.value = homeState.value.copy(
                            currentlySelectedSong = homeState.value.songs[playerState.mediaItemIdx],
                        )
                    }

                    is PlayerState.Ended -> TODO()
                    is PlayerState.Idle -> TODO()
                    PlayerState.Initial -> {
                        _uiState.value = HomeUiState.Initial
                    }

                    is PlayerState.Playing -> {
                        _homeState.value = homeState.value.copy(
                            isPlaying = playerState.isPlaying
                        )
                    }

                    is PlayerState.Progress -> {
//                        val newProgress = calculateProgressValue(playerState.progress)
//                        val newProgressString = formatDuration(state.value.progress.toLong())
//                        withContext(Dispatchers.Main) {
//                            _state.value = state.value.copy(
//                                progress = newProgress,
//                                progressString = newProgressString
//                            )
//                        }
                    }

                    is PlayerState.Ready -> {
                        _homeState.value = homeState.value.copy(
                            duration = playerState.duration
                        )
                        _uiState.value = HomeUiState.Ready
                    }
                }
            }
        }

    }

    fun getMusic(index: Int): Music = musicUseCase.music(index)

    fun prepare(music: Music) {
        music.mediaItem?.let {
            viewModelScope.launch {
                playUseCase.invoke(it)
            }
        } ?: Logger.d("failure")
    }

    fun playAndPause() {
        viewModelScope.launch {
            pauseUseCase.invoke()
        }
    }
}