package com.mygomii.domain.usecases

import com.mygomii.data.models.Track
import com.mygomii.data.repositories.TrackRepository
import javax.inject.Inject

class TrackListUseCase @Inject constructor(private val trackRepository: TrackRepository) {
    fun invoke(): List<Track> = trackRepository.getTrackList()
}