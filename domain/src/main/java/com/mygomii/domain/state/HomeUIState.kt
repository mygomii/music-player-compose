package com.mygomii.domain.state

import com.mygomii.data.models.Music

data class HomeState(
    var isLoading: Boolean = false,
    val songs: List<Music> = emptyList(),
    var duration: Long = 0,
    var progress: Float = 0f,
    var progressString: String = "00:00",
    var isPlaying: Boolean = false,
    var currentlySelectedSong: Music? = null,
    var currentlySelectedSongString: String = "00:00",
    //var isInFullScreenMode: Boolean = false,
)

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Ready : HomeUiState()
}