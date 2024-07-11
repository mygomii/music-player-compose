package com.mygomii.data.repositories

import com.mygomii.data.models.Track

interface TrackRepository {
    fun getTrackList(): List<Track>
}