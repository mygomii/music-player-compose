package com.mygomii.data.state

import com.mygomii.data.models.Track

interface PlayerEvent {
    fun onPlayPauseClick()
    fun onPreviousClick()
    fun onNextClick()
    fun onTrackClick(track: Track)
    fun onSeekBarPositionChanged(position: Long)
}