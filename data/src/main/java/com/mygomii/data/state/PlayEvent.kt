package com.mygomii.data.state

sealed class PlayerEvent {
    data object CurrentMusic : PlayerEvent()
    data object PlayPause : PlayerEvent()
    data object SkipToNext : PlayerEvent()
    data object SkipToPrevious : PlayerEvent()
    data object Backward : PlayerEvent()
    data object Forward : PlayerEvent()
    data class SeekTo(val seekPos: Long = 0) : PlayerEvent()
    data object Stop : PlayerEvent()
    data class UpdateProgress(val updatedProgress: Float) : PlayerEvent()
}