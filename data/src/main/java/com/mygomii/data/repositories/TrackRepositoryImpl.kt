package com.mygomii.data.repositories

import com.mygomii.data.getTracks
import com.mygomii.data.models.Track

class TrackRepositoryImpl: TrackRepository {
    override fun getTrackList(): List<Track> {
        return getTracks
    }
}