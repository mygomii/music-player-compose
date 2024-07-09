package com.mygomii.domain.state

import com.mygomii.data.models.Music

sealed interface UiState {
    data object Loading : UiState
    data class Success(val musics: List<Music>) : UiState
    data class Failure(val error: String) : UiState
}